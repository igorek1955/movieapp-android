package com.example.movieapp.response

class MovieId(var genres: List<Genre>) {
    constructor(): this(ArrayList())
    inner class Genre(var id: Int, var name: String ) {
        constructor(): this(0, "")
    }
}