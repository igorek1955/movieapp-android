package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityMovieDetailsBinding
import com.example.movieapp.models.MovieModel

class MovieDetails : AppCompatActivity() {

    private var movieModel: MovieModel? = null
    lateinit var binding: ActivityMovieDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromIntent()
        setViews()
    }

    private fun setViews() {
        if (movieModel != null) {
            Glide.with(this)
                .load(com.example.movieapp.utils.Credentials.BASE_URL_IMAGES + movieModel?.poster_path)
                .into(binding.imageViewDetails)
            binding.textViewTitleDetails.text = movieModel?.title.toString()
            binding.textViewDescDetails.text = movieModel?.movie_overview
            binding.ratingBarDetails.rating = movieModel?.vote_average!! / 2
        }
    }

    private fun getDataFromIntent() {
        if (intent.hasExtra("movie")) {
            movieModel = intent.getParcelableExtra("movie")
        }
    }
}