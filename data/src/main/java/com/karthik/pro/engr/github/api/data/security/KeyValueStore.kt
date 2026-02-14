package com.karthik.pro.engr.github.api.data.security

import kotlinx.coroutines.flow.Flow

interface KeyValueStore {
    suspend fun getString(key: String): String?
    suspend fun putString(key: String, value: String)

    suspend fun remove(key:String)

    fun observeString(key: String): Flow<String?>
}