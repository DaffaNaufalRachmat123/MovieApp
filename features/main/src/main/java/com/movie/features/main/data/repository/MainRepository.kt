package com.movie.features.main.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.movie.features.main.api.MainApi
import com.movie.features.main.data.datasource.MovieDataSource
import com.movie.features.main.data.datasource.ReviewDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApi : MainApi) {
    fun getMoviesByCategory(sortBy : String , withGenres : String) = Pager(
        config = PagingConfig(enablePlaceholders = true , pageSize = 10 , prefetchDistance = 1),
        pagingSourceFactory = {
            MovieDataSource(mainApi , sortBy , withGenres)
        }
    ).flow
    fun getReviews(movieId: String) = Pager(
        config = PagingConfig(enablePlaceholders = true , pageSize = 10 , prefetchDistance = 1),
        pagingSourceFactory = {
            ReviewDataSource(mainApi , movieId)
        }
    ).flow
    suspend fun getMovieCategories() = mainApi.getMovieCategories()
    suspend fun getMovieDetail(movieId : String) = mainApi.getMovieDetail(movieId)
    suspend fun getTrailerVideos(movieId: String) = mainApi.getTrailerVideos(movieId)
}