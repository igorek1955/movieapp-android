package com.example.movieapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieListItemBinding
import com.example.movieapp.models.MovieModel
import com.example.movieapp.utils.Credentials
import com.example.movieapp.utils.GenreIdService

class MovieRecyclerAdapter(private val onMovieListener: OnMovieListener) :
    ListAdapter<MovieModel, MovieViewHolder>(MovieDiffCallback()) {

    companion object {
        const val DISPLAY_POP = 1
        const val DISPLAY_SEARCH = 2

    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onMovieListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (Credentials.POPULAR) {
            DISPLAY_POP
        } else DISPLAY_SEARCH
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        try {
            val itemViewType = getItemViewType(position)
            if (itemViewType == DISPLAY_SEARCH) {
                holder.ratingBar.rating = currentList[position].vote_average / 2
                if (!currentList[position].poster_path.isNullOrEmpty()) {
                    Glide.with(holder.itemView.context)
                        .load(Credentials.BASE_URL_IMAGES + currentList[position].poster_path)
                        .into(holder.imageView)
                }
            } else {
                holder.ratingBar.rating = currentList[position].vote_average / 2
                Glide.with(holder.itemView.context)
                    .load(Credentials.BASE_URL_IMAGES + currentList[position].poster_path)
                    .into(holder.imageView)
            }

        } catch (e: Exception) {
            Log.e(
                "ERROR",
                "MovieRecycleAdapter: onBindViewHolder ERROR: item: ${currentList[position].toString()} ${e.message} , stacktrace: ${e.stackTrace.toString()}"
            )
        }
    }
}