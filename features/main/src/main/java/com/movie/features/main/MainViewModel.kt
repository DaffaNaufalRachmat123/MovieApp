package com.movie.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.movie.common.Result
import com.movie.common.extension.errorMessage
import com.movie.common.setError
import com.movie.common.setLoading
import com.movie.common.setSuccess
import com.movie.features.main.data.model.category.CategoryResponse
import com.movie.features.main.data.model.movie.MovieDetailResponse
import com.movie.features.main.data.model.video.VideoResponse
import com.movie.features.main.data.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val movieCategoriesResponse = MutableLiveData<Result<CategoryResponse>>()
    val movieDetailResponse = MutableLiveData<Result<MovieDetailResponse>>()
    val trailerVideoResponse = MutableLiveData<Result<VideoResponse>>()

    fun getMovieCategories() = viewModelScope.launch {
        runCatching {
            movieCategoriesResponse.setLoading()
            mainRepository.getMovieCategories()
        }.onSuccess {
            movieCategoriesResponse.setSuccess(it)
        }.onFailure {
            movieCategoriesResponse.setError(it.errorMessage())
        }
    }

    fun getMovieDetail(movieId : String) = viewModelScope.launch {
        runCatching {
            movieDetailResponse.setLoading()
            mainRepository.getMovieDetail(movieId)
        }.onSuccess {
            movieDetailResponse.setSuccess(it)
        }.onFailure {
            movieDetailResponse.setError(it.errorMessage())
        }
    }

    fun getTrailerVideos(movieId : String) = viewModelScope.launch {
        runCatching {
            trailerVideoResponse.setLoading()
            mainRepository.getTrailerVideos(movieId)
        }.onSuccess {
            trailerVideoResponse.setSuccess(it)
        }.onFailure {
            trailerVideoResponse.setError(it.errorMessage())
        }
    }

    fun getMoviesByCategory(sortBy : String , withGenres : String) = mainRepository.getMoviesByCategory(sortBy , withGenres).cachedIn(viewModelScope).asLiveData()
    fun getReviews(movieId: String) = mainRepository.getReviews(movieId).cachedIn(viewModelScope).asLiveData()
}