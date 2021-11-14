package com.example.movieapp.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.MovieModel
import com.example.movieapp.repositories.MovieRepository


class MainActivityViewModel: ViewModel() {
    private var movieRepository = MovieRepository.getInstance()

    fun searchMovieNextPage() {
        movieRepository.searchNextPage()
    }

    //movies by name
    fun getMovies(): LiveData<List<MovieModel>?> {
        return movieRepository.getMovies()
    }

    fun searchMovieByName(query: String, pageNumber: Int) {
        movieRepository.searchMovieByName(query, pageNumber)
    }

    //popular movies
    fun getPopularMovies(): LiveData<List<MovieModel>?> {
        return movieRepository.getPopularMovies()
    }

    fun searchPopularMovies(pageNumber: Int) {
        movieRepository.searchPopularMovies(pageNumber)
    }

    //movies by category
    fun getMoviesByCategory(): LiveData<List<MovieModel>?> {
        return movieRepository.getMoviesByCategory()
    }

    fun searchMoviesByCategory(categoryName: String, pageNumber: Int) {
        movieRepository.searchMoviesByCategory(categoryName, pageNumber)
    }

}