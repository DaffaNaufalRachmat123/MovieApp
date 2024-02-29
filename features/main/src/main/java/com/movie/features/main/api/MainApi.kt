package com.movie.features.main.api

import com.movie.features.main.data.model.category.CategoryResponse
import com.movie.features.main.data.model.movie.MovieDetailResponse
import com.movie.features.main.data.model.movie.MovieResponse
import com.movie.features.main.data.model.review.ReviewResponse
import com.movie.features.main.data.model.video.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("genre/movie/list")
    suspend fun getMovieCategories() : CategoryResponse

    @GET("discover/movie")
    suspend fun getMovieByCategory(
        @Query("page") page : Int,
        @Query("sort_by") sortBy : String,
        @Query("with_genres") withGenres : String
    ) : MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId : String
    ) : MovieDetailResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId : String,
        @Query("page") page : Int
    ) : ReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerVideos(
        @Path("movie_id") movieId : String
    ) : VideoResponse
}