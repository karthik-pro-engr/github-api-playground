package com.karthik.pro.engr.github.api.playground.presentation.error

import com.karthik.pro.engr.github.api.data.remote.error.ApiException
import okio.IOException


object PagingErrorMapper {
    fun mapPagingError(error: Throwable): String {
        return when (error) {
            is ApiException -> {
                error.message ?: "Unknown Error"
            }

            is IOException -> {
                error.message ?: "Unknown Error"
            }

            else -> "Something went wrong"
        }
    }
}