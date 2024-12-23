package com.example.mycinema.ui.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycinema.R
import com.example.mycinema.data.model.Movie
import com.example.mycinema.ui.activities.MovieDetailActivity
import com.squareup.picasso.Picasso

class MovieAdapter(private var movies: List<Movie>, private var context : Context) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
        private val textViewStart = itemView.findViewById<TextView>(R.id.textViewStart)
        private val textViewDescription = itemView.findViewById<TextView>(R.id.textViewDescription)
        private val textViewDuration = itemView.findViewById<TextView>(R.id.textViewDuration)
        private val imageView = itemView.findViewById<ImageView>(R.id.imageViewMovie)

        fun bind(movie: Movie) {
            textViewTitle.text = movie.name
            textViewStart.text = movie.startDate
            textViewDescription.text = movie.resume
            textViewDuration.text = movie.duration

            if(movie.name.length > 20) textViewTitle.text = movie.name.slice(0..20) + "..."
            if(movie.resume.length > 60) textViewDescription.text = movie.resume.slice(0..60) + "..."

            Picasso.get()
                .load(movie.imageURL)
                .fit()
                .into(imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("movieId", movie.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movies.size
}