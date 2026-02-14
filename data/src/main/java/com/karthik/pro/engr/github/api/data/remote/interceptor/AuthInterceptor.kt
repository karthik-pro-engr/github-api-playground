package com.karthik.pro.engr.github.api.data.remote.interceptor

import android.util.Log
import com.karthik.pro.engr.github.api.data.di.security.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider.getTokenSync()
        return chain.proceed(
            if (token.isNullOrEmpty()) {
                originalRequest
            } else {
                originalRequest
                    .newBuilder()
                    .header("Authorization", "Bearer $token")
                    .header("Accept", "application/vnd.github+json")
                    .build()
            }
        )
    }
}