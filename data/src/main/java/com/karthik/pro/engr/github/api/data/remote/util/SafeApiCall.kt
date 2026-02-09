package com.karthik.pro.engr.github.api.data.remote.util

import com.karthik.pro.engr.github.api.data.remote.error.NetworkError
import com.karthik.pro.engr.github.api.domain.result.Result
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(api: suspend () -> T): Result<T, NetworkError> {
    return try {
        Result.Success<T>(api())
    } catch (e: HttpException) {
        Result.Failure(NetworkError.Http(e.code()))
    } catch (e: IOException) {
        Result.Failure(NetworkError.NoInternet)
    } catch (e: Exception) {
        Result.Failure(NetworkError.Unknown)
    }

}