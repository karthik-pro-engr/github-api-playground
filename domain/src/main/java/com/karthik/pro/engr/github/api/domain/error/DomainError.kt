package com.karthik.pro.engr.github.api.domain.error

sealed class DomainError {
    object Network : DomainError()
    object Unauthorized : DomainError()
    object NotFound : DomainError()
    object RateLimited : DomainError()
    object Unknown : DomainError()
}