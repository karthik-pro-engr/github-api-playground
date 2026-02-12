package com.karthik.pro.engr.github.api.domain.security

interface TokenStorage {
    suspend fun save(token: String)
    suspend fun read(): String
    suspend fun clear()
}