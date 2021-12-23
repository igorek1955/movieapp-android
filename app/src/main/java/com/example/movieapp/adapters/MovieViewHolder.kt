package com.example.movieapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieListItemBinding

class MovieViewHolder(binding: MovieListItemBinding, private val onMovieListener: OnMovieListener) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    val imageView: ImageView = binding.imageViewMovieItem
    val ratingBar: RatingBar = binding.ratingBarMovieItem

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onMovieListener.onMovieClick(adapterPosition)
    }
}