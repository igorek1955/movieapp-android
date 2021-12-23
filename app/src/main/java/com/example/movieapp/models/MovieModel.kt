package com.example.movieapp.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    var title: String?,
    var poster_path: String?,
    private var release_date: String?,
    var runtime: Int = 0,
    var movie_id: Int = 0,
    var vote_average: Float = 0f,
    @SerializedName("overview")
    var movie_overview: String?,
    var original_language: String?,
    var genre_ids: ArrayList<Int>
) : Parcelable