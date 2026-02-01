package com.karthik.pro.engr.github.api.common.util

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable, val code: Int? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
}