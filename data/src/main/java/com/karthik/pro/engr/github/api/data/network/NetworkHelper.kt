package com.karthik.pro.engr.github.api.data.network

import retrofit2.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import com.karthik.pro.engr.github.api.common.util.Result


suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> {
    return try {
        val result = withContext(Dispatchers.IO) { call() }
        Result.Success(result)
    } catch (e: HttpException) {
        Result.Error(e, e.code())
    } catch (e: IOException) {
        Result.Error(e, null)
    } catch (e: Exception) {
        Result.Error(e, null)
    }
}