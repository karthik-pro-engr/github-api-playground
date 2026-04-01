package com.karthik.pro.engr.github.api.data.remote.error

import com.karthik.pro.engr.github.api.data.remote.dto.error.ErrorDto
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ErrorParser @Inject constructor(private val retrofit: Retrofit) {
    private val converter =
        retrofit.responseBodyConverter<ErrorDto>(ErrorDto::class.java, arrayOf())

    fun parse(response: Response<*>): ErrorDto {
        val errorBody = response.errorBody()
        if (errorBody != null) {
            try {
                val dto = converter.convert(errorBody)
                if (dto != null) {
                    return dto
                }
            } catch (t: Throwable) {
                try {
                    val raw = errorBody.string()
                    return ErrorDto(raw)
                } catch (_: Throwable) {
                }
            }
        }

        return ErrorDto(response.message())

    }
}