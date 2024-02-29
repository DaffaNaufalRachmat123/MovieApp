package com.movie.common

import com.movie.common.model.ErrorResponse

sealed class ResponseException : Exception() {
    data class Error(val errorResponse: ErrorResponse) : ResponseException()
    object Empty : ResponseException()
}
