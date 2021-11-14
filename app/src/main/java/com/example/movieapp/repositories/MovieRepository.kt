package com.example.movieapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.models.MovieModel
import com.example.movieapp.rest.MovieApiClient
import com.example.movieapp.utils.GenreIdService

class MovieRepository {

    private var mQuery: String = ""
    private var pageNum: Int = 1

    private var movieApiClient: MovieApiClient = MovieApiClient.getInstance()

    companion object {
        private var instance: MovieRepository? = null
        fun getInstance(): MovieRepository {
            if (instance == null) {
                instance = MovieRepository()
            }
            return instance!!
        }
    }

    fun getMovies(): LiveData<List<MovieModel>?> {
        return movieApiClient.mMovies
    }

    fun searchMovieByName(query: String, pageNumber: Int) {
        mQuery = query
        pageNum = pageNumber
        movieApiClient.searchMoviesApi(query, pageNumber)
    }

    fun searchNextPage() {
        searchMovieByName(mQuery, ++pageNum)
    }

    fun searchPopularMovies(pageNumber: Int) {
        mQuery = ""
        pageNum = pageNumber
        movieApiClient.searchMoviesPop(pageNumber)
    }

    fun searchMoviesByCategory(categoryName: String, pageNumber: Int) {
        mQuery = ""
        pageNum = pageNumber
        val catId = GenreIdService.getIdByName(categoryName)
        movieApiClient.searchMoviesByCategory(catId, pageNumber)
    }



    fun getPopularMovies(): LiveData<List<MovieModel>?> {
        return movieApiClient.mMoviesPopular
    }

    fun getMoviesByCategory(): LiveData<List<MovieModel>?> {
        return movieApiClient.mMoviesCategory
    }
}