package com.example.movieapp.response

import com.example.movieapp.models.MovieModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


// This class is for getting multiple movies (Movies list) - popular movies
class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    var total_count = 0

    @SerializedName("results")
    @Expose
    var movies: List<MovieModel>? = null

    override fun toString(): String {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}'
    }
}