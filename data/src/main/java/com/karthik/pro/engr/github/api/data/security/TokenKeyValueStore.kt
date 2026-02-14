package com.karthik.pro.engr.github.api.data.security

import kotlinx.coroutines.flow.Flow

interface TokenKeyValueStore {
    suspend fun getString(key: String): String?
    suspend fun putString(key: String, value: String)
    fun observeString(key: String): Flow<String?>
}