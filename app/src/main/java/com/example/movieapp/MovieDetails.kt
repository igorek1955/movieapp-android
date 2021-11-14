package com.example.movieapp

import android.net.Credentials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.movieapp.models.MovieModel

class MovieDetails : AppCompatActivity() {


    //widgets
    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var ratingBar: RatingBar

    private var movieModel: MovieModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        initVars()
        getDataFromIntent()
        setViews()
    }

    private fun setViews() {
        if (movieModel != null) {
            Glide.with(this)
                .load(com.example.movieapp.utils.Credentials.BASE_URL_IMAGES + movieModel?.poster_path)
                .into(imageView)
            titleView.text = movieModel?.title.toString()
            descriptionView.text = movieModel?.movie_overview
            ratingBar.rating = movieModel?.vote_average!! / 2
        }
    }

    private fun getDataFromIntent() {
        if (intent.hasExtra("movie")) {
            movieModel = intent.getParcelableExtra("movie")
        }
    }

    private fun initVars() {
        imageView = findViewById(R.id.imageView_details)
        titleView = findViewById(R.id.textView_title_details)
        descriptionView = findViewById(R.id.textView_desc_details)
        ratingBar = findViewById(R.id.ratingBar_details)
    }
}