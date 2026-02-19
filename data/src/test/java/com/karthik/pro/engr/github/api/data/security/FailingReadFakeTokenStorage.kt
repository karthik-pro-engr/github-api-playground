package com.karthik.pro.engr.github.api.data.security

import com.karthik.pro.engr.github.api.domain.security.TokenStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FailingReadFakeTokenStorage(initial: String? = null) : TokenStorage {
    private val stateFlow = MutableStateFlow(initial)
    override suspend fun save(token: String) {
        stateFlow.value = token
    }

    override suspend fun read(): String? {
        throw RuntimeException("read failed (simulated)")
    }

    override suspend fun clear() {
        stateFlow.value = null
    }

    override fun observe(): Flow<String?> = stateFlow.asStateFlow()
}