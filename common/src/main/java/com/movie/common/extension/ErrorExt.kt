package com.movie.common.extension

import com.github.ajalt.timberkt.d
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

fun Boolean?.orFalse(): Boolean = this ?: false

fun Throwable.errorMessage(): String {
    val msg: String

    when (this) {
        is HttpException -> {
            d { "Error Type : HttpException" }
            val responseBody = this.response()?.errorBody()
            val code = response()?.code()
            msg = when (code) {
                500 -> {
                    "Terjadi masalah dengan server kami, coba lagi nanti"
                }
                409 -> {
                    "Pengguna sudah ada"
                }
                404 -> {
                    "Pengguna tidak ditemukan"
                }
                401 -> {
                    "Password salah"
                }
                400 -> {
                    "Format email salah"
                }
                else -> {
                    responseBody.getErrorMessage()
                }
            }
            println("HttpException checkApiError onError Code : $code : $msg")

        }
        is SocketTimeoutException -> {
            msg = "Timeout, Coba lagi"
        }
        else -> {
            d { "Error Type : $message"}
            msg = if (message == null || message?.startsWith("Unable to resolve host").orFalse() || message == "null" || message?.startsWith("Failed to connect to").orFalse()
                || message?.startsWith("Write error: ssl").orFalse() || message?.startsWith("Value <!DOCTYPE ").orFalse()
            ) {
                "Tidak ada koneksi jaringan"
            } else {
                message ?: "Terjadi kesalahan, silahkan coba lagi"
            }


        }
    }
    println("ApiOnError : $msg")
    return msg
}

fun ResponseBody?.getErrorMessage(): String {
    return try {
        val jsonObject = JSONObject(this?.string() ?: "")
        println("jsonObjectError : $jsonObject")
        val errorMsg: String = when {
            jsonObject.has("message") -> jsonObject.getString("message")
            jsonObject.has("errors") -> jsonObject.getString("errors")
            jsonObject.has("error") -> jsonObject.getString("error")
            jsonObject.has("info") -> jsonObject.getString("info")
            else -> "Terjadi kesalahan, Coba lagi"
        }
        return errorMsg
    } catch (e: JSONException) {
        e.message.toString()
    }
}