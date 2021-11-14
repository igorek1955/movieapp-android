package com.example.movieapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R

class PopularMovieHolder(itemView: View, var listener: OnMovieListener):
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var imageView_pop: ImageView
    var ratingBar_pop: RatingBar

    override fun onClick(v: View) {
        listener.onMovieClick(adapterPosition)
    }

    init {
        imageView_pop = itemView.findViewById(R.id.movie_img_popualar)
        ratingBar_pop = itemView.findViewById(R.id.rating_bar_pop)
        itemView.setOnClickListener(this)
    }
}