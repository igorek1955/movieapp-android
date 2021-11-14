package com.example.movieapp.rest

import com.example.movieapp.utils.Credentials
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestService {
    companion object {
        private var retrofitBuilder = Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        private var retrofitService: Retrofit = retrofitBuilder.build()
        private var movieApi: MovieApi = retrofitService.create(MovieApi::class.java)
        fun getMovieApi(): MovieApi {
            return movieApi
        }
    }
}

