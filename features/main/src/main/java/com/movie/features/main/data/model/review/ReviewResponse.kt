package com.movie.features.main.data.model.review

data class ReviewResponse(
    val id : Int = 0,
    val page : Int = 0,
    val results : MutableList<Review> = arrayListOf()
)
data class Review(
    val id : String = "",
    val author : String = "",
    val author_details : AuthorDetail,
    val content : String = "",
    val created_at : String = "",
    val updated_at : String = "",
    val url : String = ""
)

data class AuthorDetail(
    val name : String = "",
    val username : String = "",
    val avatar_path : String = "",
    val rating : Double
)