package com.example.mycinema.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mycinema.R
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)

        val movieId = intent.getStringExtra("movieId").toString()
        getMovie(movieId)
    }

    fun getMovie(id:String){
        val db = FirebaseFirestore.getInstance()
        db.collection("peliculas").document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val textViewMovieTitle = this.findViewById<TextView>(R.id.textViewMovieName)
                    val textViewMovieDescription = this.findViewById<TextView>(R.id.textViewMovieDescription)
                    val textViewMovieDuration = this.findViewById<TextView>(R.id.DurationTextView)
                    val textViewMovieStart = this.findViewById<TextView>(R.id.startDateTextView)
                    val imageView = this.findViewById<ImageView>(R.id.imageViewMovieDetail)

                    textViewMovieTitle.text = document.data?.get("nombre")?.toString() ?: "";
                    textViewMovieDescription.text = document.data?.get("resumen")?.toString() ?: "";
                    textViewMovieDuration.text = document.data?.get("duracionHoras")?.toString() ?: "";

                    val timestamp = document.getTimestamp("inicio")  // Campo de tipo Timestamp

                    timestamp?.let {
                        val formattedDate = formatearTimestamp(it)
                        textViewMovieStart.text = formattedDate;
                    }

                    Picasso.get()
                        .load(document.data?.get("urlImagen").toString())
                        .into(imageView)

                } else {
                    finish()
                }
            }
            .addOnFailureListener { e ->
                finish()
            }
    }

    fun formatearTimestamp(timestamp: Timestamp): String {
        val date = timestamp.toDate()  // Convierte Timestamp a Date
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())  // Define el formato
        return formatter.format(date)  // Devuelve la fecha formateada
    }
}