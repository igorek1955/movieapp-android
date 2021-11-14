package com.example.movieapp.response

import com.example.movieapp.models.MovieModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


// This class is for single movie request
class MovieResponse {
    @SerializedName("results")
    @Expose
    var movie: MovieModel? = null

    override fun toString(): String {
        return "MovieResponse{" +
                "movie=" + movie +
                '}'
    }
}