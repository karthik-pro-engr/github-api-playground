package com.karthik.pro.engr.github.api.domain.security

import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    suspend fun save(token: String)
    suspend fun read(): String?
    suspend fun clear()
     fun observe(): Flow<String?>
}