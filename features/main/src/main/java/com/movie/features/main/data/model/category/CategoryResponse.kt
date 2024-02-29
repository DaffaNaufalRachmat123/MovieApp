package com.movie.features.main.data.model.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @Expose
    @SerializedName("genres")
    val genres : MutableList<Category> = arrayListOf()
)

data class Category(
    @Expose
    @SerializedName("id")
    val id : Int ,
    @Expose
    @SerializedName("name")
    val name : String
)