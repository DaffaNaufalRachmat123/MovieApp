package com.movie.features.main.data.model.movie

data class MovieResponse(
    val page : Int = 0,
    val results : MutableList<Movie> = arrayListOf()
)

data class Movie(
    val id : Int = 0,
    val adult : Boolean = false,
    val backdrop_path : String = "",
    val genre_ids : MutableList<Int> = arrayListOf(),
    val original_language : String = "",
    val original_title : String = "",
    val overview : String = "",
    val popularity : Double,
    val poster_path : String = "",
    val release_date : String = "",
    val title : String = "",
    val video : Boolean = false,
    val vote_average : Double = 0.0,
    val vote_count : Int = 0
)