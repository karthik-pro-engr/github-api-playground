package com.karthik.pro.engr.github.api.data.security

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.ConcurrentHashMap

class FakeKeyValueStore : KeyValueStore {
    private val fakeDataStore = ConcurrentHashMap<String, String?>()
    private val fakeObserverFlow = ConcurrentHashMap<String, MutableStateFlow<String?>>()
    override suspend fun getString(key: String): String? {
        return fakeDataStore[key]
    }

    override suspend fun putString(key: String, value: String) {
        fakeDataStore[key] = value
        fakeObserverFlow.computeIfAbsent(key) { MutableStateFlow(fakeDataStore[key]) }.value = value
    }

    override suspend fun remove(key: String) {
        fakeDataStore.remove(key)
        fakeObserverFlow.getOrPut(key) { MutableStateFlow(null) }.value = null
    }

    override fun observeString(key: String): Flow<String?> {
        return fakeObserverFlow.computeIfAbsent(key) { MutableStateFlow(fakeDataStore[key]) }
            .asStateFlow()
    }
}