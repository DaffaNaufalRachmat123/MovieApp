package com.movie.common.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int = 500,
    @SerializedName("server_message")
    val message: String? = "Internal Server Error.",
    val type: Type = Type.GENERAL,
    val title: String = "" + code
) {
    enum class Type {
        GENERAL,
        HOTSPOT_LOGIN,
        UNRESOLVED_HOST,
        NO_INTERNET_CONNECTION,
        REQUEST_TIMEOUT,
        NO_DATA
    }
}