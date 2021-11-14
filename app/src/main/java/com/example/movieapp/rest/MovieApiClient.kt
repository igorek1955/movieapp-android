package com.example.movieapp.rest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.BackgroundExecutors
import com.example.movieapp.models.MovieModel
import com.example.movieapp.response.MovieSearchResponse
import com.example.movieapp.utils.Credentials
import retrofit2.Call
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


class MovieApiClient {

    var mMovies: MutableLiveData<List<MovieModel>?> = MutableLiveData()
    var mMoviesPopular: MutableLiveData<List<MovieModel>?> = MutableLiveData()
    var mMoviesCategory: MutableLiveData<List<MovieModel>?> = MutableLiveData()


    companion object {
        private var instance: MovieApiClient? = null
        fun getInstance(): MovieApiClient {
            if (instance == null) {
                instance = MovieApiClient()
            }
            return instance!!
        }
    }

    // making Global RUNNABLE
    private var retrieveMoviesRunnable: RetrieveMoviesRunnable? = null
    private var retrieveMoviesRunnablePop: RetrieveMoviesRunnablePop? = null
    private var retrieveMoviesByCat: RetrieveMoviesByCatRunnable? = null



    fun searchMoviesByCategory(categoryId: String, pageNumber: Int) {
        if (retrieveMoviesByCat != null) {
            retrieveMoviesByCat = null
        }
        retrieveMoviesByCat = RetrieveMoviesByCatRunnable(categoryId, pageNumber)
        val myHandler: Future<*> = BackgroundExecutors.getInstance().mNetworkIO.submit(retrieveMoviesByCat)

        BackgroundExecutors.getInstance().mNetworkIO.schedule({
            //cancelling retrofit call
            myHandler.cancel(true)
        }, 5000, TimeUnit.MILLISECONDS)
    }

    fun searchMoviesPop(pageNumber: Int) {
        if (retrieveMoviesRunnablePop != null) {
            retrieveMoviesRunnablePop = null
        }
        retrieveMoviesRunnablePop = RetrieveMoviesRunnablePop(pageNumber)
        val myHandler: Future<*> = BackgroundExecutors.getInstance().mNetworkIO.submit(retrieveMoviesRunnablePop)
        BackgroundExecutors.getInstance().mNetworkIO.schedule(Runnable { // Cancelling the retrofit call
            myHandler.cancel(true)
        }, 1000, TimeUnit.MILLISECONDS)
    }

    fun searchMoviesApi(query: String, pageNumber: Int) {
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null
        }
        retrieveMoviesRunnable = RetrieveMoviesRunnable(query, pageNumber)
        val myHandler: Future<*> = BackgroundExecutors.getInstance().mNetworkIO.submit(retrieveMoviesRunnable)

        BackgroundExecutors.getInstance().mNetworkIO.schedule({
            //cancelling retrofit call
            myHandler.cancel(true)
        }, 5000, TimeUnit.MILLISECONDS)
    }


    inner class RetrieveMoviesRunnable(var query: String, var pageNumber: Int): Runnable {
        private var cancelRequest: Boolean = false

        override fun run() {
            try {
                val response = getMovies(query, pageNumber).execute()
                if (cancelRequest) return

                if (response.code() == 200) {
                    val responseBody: MovieSearchResponse = response.body()!!
                    Log.i("Log", "raw response: ${response.body()}")
                    val movieList: ArrayList<MovieModel> = ArrayList(responseBody.movies)
                    if (pageNumber == 1) {
                        mMovies.postValue(movieList)
                    } else {
                        val currentMovies: ArrayList<MovieModel> = ArrayList(mMovies.value!!)
                        currentMovies.addAll(movieList)
                        mMovies.postValue(currentMovies)
                    }
                } else {
                    val error = response.errorBody()!!.string()
                    Log.v("Tag", "Error $error");
                    mMovies.postValue(null);
                }
            } catch (e: Exception) {
                Log.e("ERROR", "message: ${e.message}, st: ${e.stackTrace}")
                cancelRequest()
            }

        }

        fun getMovies(query: String, pageNumber: Int): Call<MovieSearchResponse> {
            return RestService.getMovieApi().searchMovieByName(Credentials.API_KEY, query,
                pageNumber.toString()
            )
        }

        fun cancelRequest() {
            Log.v("Tag", "Cancelling Search Request" );
            cancelRequest = true
        }
    }

    inner class RetrieveMoviesRunnablePop(val pageNumber: Int) : Runnable {
        var cancelRequest = false
        override fun run() {
            try {
                val response = getPop(pageNumber).execute()
                if (cancelRequest) return

                if (response.code() == 200) {
                    val responseBody: MovieSearchResponse = response.body()!!
                    Log.i("Log", "raw response: ${response.body()}")
                    val movieList: ArrayList<MovieModel> = ArrayList(responseBody.movies)
                    if (pageNumber == 1) {
                        mMoviesPopular.postValue(movieList)
                    } else {
                        val currentMovies: ArrayList<MovieModel> = ArrayList(mMoviesPopular.value!!)
                        currentMovies.addAll(movieList)
                        mMoviesPopular.postValue(currentMovies)
                    }
                } else {
                    val error = response.errorBody()!!.string()
                    Log.v("Tag", "Error $error");
                    mMoviesPopular.postValue(null);
                }
            } catch (e: Exception) {
                Log.e("ERROR", "message: ${e.message}, st: ${e.stackTrace}")
                cancelRequest()
            }
        }

        // Search Method/ query
        private fun getPop(pageNumber: Int): Call<MovieSearchResponse> {
            return RestService.getMovieApi().searchPopularMovies(
                Credentials.API_KEY,
                pageNumber
            )
        }

        private fun cancelRequest() {
            Log.v("Tag", "Cancelling Search Request")
            cancelRequest = true
        }
    }

    inner class RetrieveMoviesByCatRunnable(var categoryId: String, var pageNumber: Int): Runnable {
        private var cancelRequest: Boolean = false


        override fun run() {
            try {
                val response = getMovies(categoryId, pageNumber).execute()
                if (cancelRequest) return

                if (response.code() == 200) {
                    val responseBody: MovieSearchResponse = response.body()!!
                    Log.i("Log", "raw response: ${response.body()}")
                    val movieList: ArrayList<MovieModel> = ArrayList(responseBody.movies)
                    if (pageNumber == 1) {
                        mMoviesCategory.postValue(movieList)
                    } else {
                        val currentMovies: ArrayList<MovieModel> = ArrayList(mMoviesCategory.value!!)
                        currentMovies.addAll(movieList)
                        mMoviesCategory.postValue(currentMovies)
                    }
                } else {
                    val error = response.errorBody()!!.string()
                    Log.v("Tag", "Error $error");
                    mMoviesCategory.postValue(null);
                }
            } catch (e: Exception) {
                Log.e("ERROR", "message: ${e.message}, st: ${e.stackTrace}")
                cancelRequest()
            }

        }

        fun getMovies(categoryId: String, pageNumber: Int): Call<MovieSearchResponse> {
            return RestService.getMovieApi().searchMoviesByCategory(Credentials.API_KEY, categoryId,
                pageNumber)
        }

        fun cancelRequest() {
            Log.v("Tag", "Cancelling Search Request" );
            cancelRequest = true
        }
    }

}