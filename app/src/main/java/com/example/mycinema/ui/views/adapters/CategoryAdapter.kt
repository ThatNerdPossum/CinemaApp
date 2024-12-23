package com.example.mycinema.ui.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycinema.R
import com.example.mycinema.data.model.Category

class CategoryAdapter(private var categories: List<Category>, private var context: Context) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewCategoryName = itemView.findViewById<TextView>(R.id.textViewCategoryName)
        private val recyclerViewMovies = itemView.findViewById<RecyclerView>(R.id.recyclerViewMovies)

        fun bind(category: Category) {
            textViewCategoryName.text = category.name
            recyclerViewMovies.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewMovies.adapter = MovieAdapter(category.movies, context)
        }
    }

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_category_item_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}