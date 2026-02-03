package com.karthik.pro.engr.github.api.data.remote.error

sealed class NetworkError {
    object NoInternet : NetworkError()
    data class Http(val code: Int) : NetworkError()
    object Unknown : NetworkError()
}
