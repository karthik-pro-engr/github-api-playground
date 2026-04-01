package com.karthik.pro.engr.github.api.data.remote.error

import okhttp3.Headers

class ApiException(
    override val message: String?,
    val code: Int? = null,
    val headers: Headers? = null
) : Exception(message)