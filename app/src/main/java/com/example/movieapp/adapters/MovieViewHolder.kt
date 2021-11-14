package com.example.movieapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.models.MovieModel

class MovieViewHolder(itemView: View, private val onMovieListener: OnMovieListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val imageView: ImageView = itemView.findViewById(R.id.image_view_movie_item)
    val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar_movie_item)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onMovieListener.onMovieClick(adapterPosition)
    }
}