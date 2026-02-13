package com.karthik.pro.engr.github.api.data.di.security

import com.karthik.pro.engr.github.api.domain.security.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject


class TokenProvider @Inject constructor(private val tokenStorage: TokenStorage,
    @param:ApplicationScope private val scope: CoroutineScope) {


    private val _cachedToken = AtomicReference<String?>(null)

    init {

        tokenStorage.observe().onEach { value ->
            _cachedToken.set(value)
        }.launchIn(scope)

        scope.launch {
            try {
                val initial = tokenStorage.read()
                if (initial != null) {
                    _cachedToken.set(initial)
                }
            } catch (_: Throwable) {
                // ignore - will be populated by flow eventually
            }

        }
    }

    fun getTokenSync(): String? = _cachedToken.get()
}