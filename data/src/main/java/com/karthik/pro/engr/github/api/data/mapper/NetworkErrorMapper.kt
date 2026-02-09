package com.karthik.pro.engr.github.api.data.mapper

import com.karthik.pro.engr.github.api.data.remote.error.NetworkError
import com.karthik.pro.engr.github.api.domain.error.DomainError

fun NetworkError.toDomainError(): DomainError {
    return when (this) {
        is NetworkError.Http -> when (code) {
            401 -> DomainError.Unauthorized
            404 -> DomainError.NotFound
            429 -> DomainError.RateLimited
            else -> DomainError.Unknown
        }

        NetworkError.NoInternet -> DomainError.Network
        NetworkError.Unknown -> DomainError.Unknown
    }
}