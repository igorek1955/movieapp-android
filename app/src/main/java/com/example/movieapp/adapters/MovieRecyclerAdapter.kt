package com.example.movieapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.MovieModel
import com.example.movieapp.utils.Credentials
import com.example.movieapp.utils.GenreIdService

class MovieRecyclerAdapter (private val onMovieListener: OnMovieListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val DISPLAY_POP = 1
        const val DISPLAY_SEARCH = 2

    }

    var movieList: List<MovieModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DISPLAY_SEARCH) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
            return MovieViewHolder(view, onMovieListener)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.popular_layout, parent, false)
            return PopularMovieHolder(view, onMovieListener)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {

            val itemViewType = getItemViewType(position)
            if (itemViewType == DISPLAY_SEARCH) {
                val movieHolder = holder as MovieViewHolder
                movieHolder.ratingBar.rating = movieList[position].vote_average / 2
                if (!movieList[position].poster_path.isNullOrEmpty()) {
                    Glide.with(holder.itemView.context)
                        .load(Credentials.BASE_URL_IMAGES + movieList[position].poster_path)
                        .into(movieHolder.imageView)
                }
            } else {
                val movieHolder = holder as PopularMovieHolder
                movieHolder.ratingBar_pop.rating = movieList[position].vote_average / 2
                Glide.with(holder.itemView.context)
                    .load(Credentials.BASE_URL_IMAGES + movieList[position].poster_path)
                    .into(movieHolder.imageView_pop)
            }

        } catch (e: Exception) {
            Log.e("ERROR", "MovieRecycleAdapter: onBindViewHolder ERROR: item: ${movieList[position].toString()} ${e.message} , stacktrace: ${e.stackTrace.toString()}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (Credentials.POPULAR) {
            DISPLAY_POP
        } else DISPLAY_SEARCH
    }
}