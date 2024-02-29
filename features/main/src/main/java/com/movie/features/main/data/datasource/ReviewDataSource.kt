package com.movie.features.main.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movie.common.ResponseException
import com.movie.common.model.ErrorResponse
import com.movie.features.main.api.MainApi
import com.movie.features.main.data.model.review.Review
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ReviewDataSource(
    private val mainApi : MainApi,
    private val movieId : String
) : PagingSource<Int , Review>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: 1
        val prevKey = if(page == 1) null else page - 1
        val loadSize = 10
        return try {
            val data = mainApi.getReviews(movieId , page)
            if(data.results.isNotEmpty()){
                LoadResult.Page(
                    data = data.results,
                    prevKey = if(page == 0) null else page - loadSize,
                    nextKey = if (data.results.isEmpty()) null else page + loadSize
                )
            } else {
                LoadResult.Error(ResponseException.Empty)
            }
        } catch (exception : Exception){
            when(exception){
                is IOException -> LoadResult.Error(
                    ResponseException.Error(
                        ErrorResponse(
                            type = ErrorResponse.Type.NO_INTERNET_CONNECTION,
                            message = "No internet connection"
                        )
                    )
                )
                is UnknownHostException -> LoadResult.Error(
                    ResponseException.Error(
                        ErrorResponse(
                            code = 502,
                            type = ErrorResponse.Type.UNRESOLVED_HOST,
                            message = "Bad gateway"
                        )
                    )
                )
                is SocketTimeoutException -> LoadResult.Error(
                    ResponseException.Error(
                        ErrorResponse(
                            code = 408,
                            type = ErrorResponse.Type.REQUEST_TIMEOUT,
                            message = "Request timeout"
                        )
                    )
                )
                is HttpException -> {
                    if(exception.code() == 302) LoadResult.Error(
                        ResponseException.Error(
                            ErrorResponse(
                                type = ErrorResponse.Type.HOTSPOT_LOGIN
                            )
                        )
                    ) else if(exception.code() == 404) LoadResult.Error(
                        ResponseException.Empty
                    ) else LoadResult.Error(
                        ResponseException.Error(
                            ErrorResponse(
                                code = 400,
                                message = "Bad request"
                            )
                        )
                    )
                } else -> LoadResult.Error(
                    ResponseException.Error(
                        ErrorResponse(
                            code = 500,
                            type = ErrorResponse.Type.GENERAL,
                            message = "Internal Server Error."
                        )
                    )
                )
            }
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition
    }
}