package com.example.movieapp.utils

import android.util.Log
import com.example.movieapp.response.MovieId
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class GenreIdService {
    companion object {
        val idNameMap: HashMap<Int, String> = HashMap()
        val nameIdMap: HashMap<String, Int> = HashMap()

        fun init() {
            if (idNameMap.size == 0) getNameById(0)
        }

        fun getNameById(id: Int): String {
            try {
                if (idNameMap.size == 0) {
                    GlobalScope.launch(Dispatchers.IO) {
                        val url = URL(Credentials.BASE_URL + "genre/movie/list?api_key=${Credentials.API_KEY}")
                        val httpUrlConnection = url.openConnection() as HttpURLConnection
                        httpUrlConnection.connect()
                        if (httpUrlConnection.responseCode == 200) {
                            val data = httpUrlConnection.inputStream.bufferedReader().readText()
                            Log.i("INFO","GenreIdService: getNameById INFO: raw response $data" )
                            val movieId: MovieId? = Gson().fromJson(
                                data,
                                MovieId::class.java
                            )
                            if (movieId != null) {
                                for (genre in movieId.genres) {
                                    idNameMap[genre.id] = genre.name
                                    nameIdMap[genre.name] = genre.id
                                }
                            }
                            if (idNameMap.size > 0) {
                                Log.i("INFO","GenreIdService: getNameById INFO: genreIdMap init success $idNameMap" )
                            }
                            httpUrlConnection.disconnect()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "GenreIdService: getNameById ERROR ${e.message}")
            }

            return if (idNameMap.size == 0) {
                ""
            } else {
                idNameMap[id]!!
            }
        }

        fun getIdByName(name: String): String {
            return nameIdMap[name].toString()
        }
    }
}