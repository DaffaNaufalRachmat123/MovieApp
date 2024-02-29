package com.movie.common

import androidx.lifecycle.MutableLiveData

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failed(val message: String?) : Result<Nothing>()
}

fun <T> MutableLiveData<Result<T>>.setSuccess(data: T? = null) =
    if (data != null) {
        postValue(Result.Success(data))
    } else {
        postValue(Result.Failed("Data Null"))
    }


fun <T> MutableLiveData<Result<T>>.setLoading() =
    postValue(Result.Loading)

fun <T> MutableLiveData<Result<T>>.setError(message: String? = null) =
    postValue(Result.Failed(message))