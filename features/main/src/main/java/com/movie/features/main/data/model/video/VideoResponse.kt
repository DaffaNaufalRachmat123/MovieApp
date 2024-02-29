package com.movie.features.main.data.model.video

data class VideoResponse(
    val id : Int = 0,
    val results : MutableList<Video> = arrayListOf()
)

data class Video(
    val id : String = "",
    val iso_639_1 : String = "",
    val iso_3166_1 : String = "",
    val name : String = "",
    val key : String = "",
    val site : String = "",
    val size : Int = 0,
    val type : String = "",
    val official : Boolean = false,
    val published_at : String = ""
)