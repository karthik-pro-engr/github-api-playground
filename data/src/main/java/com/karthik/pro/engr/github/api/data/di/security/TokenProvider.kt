package com.karthik.pro.engr.github.api.data.di.security

import com.karthik.pro.engr.github.api.data.security.SecureTokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
class TokenProvider @Inject constructor(private val tokenStorage: SecureTokenStorage) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @OptIn(ExperimentalAtomicApi::class)
    private val _cachedToken = AtomicReference<String?>(null)

    init {

        tokenStorage.observe().onEach { value ->
            _cachedToken.store(value)
        }.launchIn(scope)

        scope.launch {
            try {
                val initial = tokenStorage.read()
                if (initial != null) {
                    _cachedToken.store(initial)
                }
            } catch (_: Throwable) {
                // ignore - will be populated by flow eventually
            }

        }
    }

    fun getTokenSync(): String? = _cachedToken.load()
}