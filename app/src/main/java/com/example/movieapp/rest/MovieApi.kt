package com.example.movieapp.rest

import com.example.movieapp.models.MovieModel
import com.example.movieapp.response.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("search/movie")
    fun searchMovieByName(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Call<MovieSearchResponse>


    @GET("movie/{movie_id}?")
    fun searchMovieById(@Path("movie_id") movieId: Int,
                        @Query("api_key") apiKey: String): Call<MovieModel>

    @GET("movie/popular")
    fun searchPopularMovies(@Query("api_key") apiKey: String,
                            @Query("page") page: Int): Call<MovieSearchResponse>


    @GET("discover/movie")
    fun searchMoviesByCategory(
        @Query("api_key") apiKey: String,
        @Query("with_genres") id: String,
        @Query("page") page: Int): Call<MovieSearchResponse>
}
