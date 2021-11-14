package com.example.movieapp.models

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieModel(
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
) : Parcelable {

    constructor(parcel: Parcel) : this(
        "",
        "", "", 0,
        0, 0f,
        "", "", ArrayList()
    ) {
        title = parcel.readString()
        poster_path = parcel.readString()
        release_date = parcel.readString()
        runtime = parcel.readInt()
        movie_id = parcel.readInt()
        vote_average = parcel.readFloat()
        movie_overview = parcel.readString()
        original_language = parcel.readString()
        val intArray = parcel.createIntArray()
        genre_ids = intArray!!.toCollection(ArrayList())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeInt(runtime)
        parcel.writeInt(movie_id)
        parcel.writeFloat(vote_average)
        parcel.writeString(movie_overview)
        parcel.writeString(original_language)
        parcel.writeList(genre_ids)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "MovieModel(title=$title, poster_path=$poster_path, release_date=$release_date, runtime=$runtime, movie_id=$movie_id, vote_average=$vote_average, original_language=$original_language, genre_ids=$genre_ids)"
    }

    companion object CREATOR : Parcelable.Creator<MovieModel> {
        override fun createFromParcel(parcel: Parcel): MovieModel {
            return MovieModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieModel?> {
            return arrayOfNulls(size)
        }
    }

    fun setRelease_date(releaseDate: String) {
        this.release_date = release_date
    }

    @SuppressLint("NewApi")
    fun getRelease_date(): String {
        try {
            if (release_date?.length!! > 0) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val date = LocalDate.parse(this.release_date, formatter)
                return date.year.toString()
            }
        } catch (e: NullPointerException) {
            Log.e("ERROR", "MovieModel: getRelease_date ERROR couldnot parse date: $release_date")
        }
        return ""
    }


}