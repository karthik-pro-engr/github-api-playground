package com.karthik.pro.engr.github.api.data.mapper

import com.google.common.truth.Truth.assertThat
import com.karthik.pro.engr.github.api.data.remote.error.NetworkError
import com.karthik.pro.engr.github.api.domain.error.DomainError
import org.junit.Test

class NetworkErrorMapperTest {
    @Test
    fun `http 401 maps to Unauthorized`() {
        val networkError = NetworkError.Http(401)

        val domainError = networkError.toDomainError()

        assertThat(domainError).isEqualTo(DomainError.Unauthorized)
    }

    @Test
    fun `http 404 maps to NotFound`() {
        val networkError = NetworkError.Http(404)

        val domainError = networkError.toDomainError()

        assertThat(domainError).isEqualTo(DomainError.NotFound)
    }

    @Test
    fun `http 429 maps to RateLimited`() {
        val networkError = NetworkError.Http(429)

        val domainError = networkError.toDomainError()

        assertThat(domainError).isEqualTo(DomainError.RateLimited)
    }

    @Test
    fun `http unknow http code maps to Unknown`() {
        val networkError = NetworkError.Http(500)

        val domainError = networkError.toDomainError()

        assertThat(domainError).isEqualTo(DomainError.Unknown)
    }

    @Test
    fun `NoInternet maps to Network`() {
        val networkError = NetworkError.NoInternet

        val domainError = networkError.toDomainError()

        assertThat(domainError).isEqualTo(DomainError.Network)
    }

    @Test
    fun `Unknown maps to Unknown`() {
        val error = NetworkError.Unknown

        val domainError = error.toDomainError()

        assertThat(domainError).isEqualTo(DomainError.Unknown)
    }

}