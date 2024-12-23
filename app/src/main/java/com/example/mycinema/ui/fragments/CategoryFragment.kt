package com.example.mycinema.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycinema.R
import com.example.mycinema.data.model.Category
import com.example.mycinema.data.model.Movie
import com.example.mycinema.ui.views.adapters.CategoryAdapter
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class CategoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView;
    private lateinit var categoryAdapter: CategoryAdapter;
    private lateinit var spinner: ProgressBar;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.movie_category_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewMovieCategories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        categoryAdapter = CategoryAdapter(emptyList(), requireContext())
        recyclerView.adapter = categoryAdapter

        spinner = view.findViewById(R.id.movieProgressBar)

        getAllMovies()
    }

    fun getAllMovies(){
        val db = FirebaseFirestore.getInstance()
        db.collection("peliculas")
            .get()
            .addOnSuccessListener { result ->

                val categoryMap = HashMap<String, MutableList<Movie>>()
                val categoryList = mutableListOf<Category>()

                for (document in result) {

                    val movie = Movie(
                        document.id,
                        document.data["nombre"].toString(),
                        document.data["resumen"].toString(),
                        document.data["inicio"].toString(),
                        document.data["duracionHoras"].toString(),
                        document.data["genero"].toString(),
                        document.data["urlImagen"].toString(),
                    );

                    val timestamp = document.getTimestamp("inicio")  // Campo de tipo Timestamp

                    timestamp?.let {
                        val formattedDate = formatearTimestamp(it)
                        movie.startDate = formattedDate
                    }

                    if (categoryMap[movie.genre] != null) {
                        categoryMap[movie.genre]!!.add(movie)
                    } else {
                        categoryMap[movie.genre] = mutableListOf(movie)
                    }
                }

                categoryMap.forEach { e ->
                    categoryList.add(Category(e.key, e.value))
                }

                categoryAdapter.updateCategories(categoryList)
                spinner.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al obtener documentos", e)
            }
    }

    fun formatearTimestamp(timestamp: Timestamp): String {
        val date = timestamp.toDate()  // Convierte Timestamp a Date
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())  // Define el formato
        return formatter.format(date)  // Devuelve la fecha formateada
    }
}