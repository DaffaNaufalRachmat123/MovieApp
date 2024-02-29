package com.movie.features.main.data.model.movie

import com.movie.features.main.data.model.category.Category

data class MovieDetailResponse(
    val id : Int = 0,
    val adult : Boolean = false,
    val backdrop_path : String = "",
    val belongs_to_collection : Collection,
    val budget : Int = 0,
    val genres : MutableList<Category> = arrayListOf(),
    val homepage : String = "",
    val imdb_id : String = "",
    val original_language : String = "",
    val original_title : String = "",
    val overview : String = "",
    val popularity : Double = 0.0,
    val poster_path : String = "",
    val production_companies : MutableList<ProductionCompany> = arrayListOf(),
    val production_countries : MutableList<ProductionCountries> = arrayListOf(),
    val release_date : String,
    val revenue : Int = 0,
    val runtime : Int,
    val spoken_languages : MutableList<SpokenLanguage> = arrayListOf(),
    val status : String = "",
    val tagline : String = "",
    val title : String = "",
    val video : Boolean = false,
    val vote_average : Double = 0.0,
    val vote_count : Int
)

data class SpokenLanguage(
    val english_name : String = "",
    val iso_639_1 : String = "",
    val name : String = ""
)

data class Collection(
    val id : Int = 0,
    val name : String = "",
    val posterPath : String = "",
    val backdropPath : String = ""
)

data class ProductionCompany(
    val id : Int = 0,
    val logoPath : String = "",
    val name : String = "",
    val originCountry : String = ""
)

data class ProductionCountries(
    val iso_3166_1 : String = "",
    val name : String = ""
)