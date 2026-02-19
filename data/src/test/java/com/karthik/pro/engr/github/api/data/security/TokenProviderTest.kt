package com.karthik.pro.engr.github.api.data.security

import com.google.common.truth.Truth.assertThat
import com.karthik.pro.engr.github.api.data.di.security.TokenProvider
import com.karthik.pro.engr.github.api.domain.security.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TokenProviderTest {

    @Test
    fun `getTokenSync returns initial value is null when value is not assigned `() = runTest {

        val storage = FakeTokenStorage(null)
        val deps = makeDeps(backgroundScope, storage)

        advanceUntilIdle()

        val token = deps.provider.getTokenSync()
        assertThat(token).isNull()

    }

    @Test
    fun `getTokenSync returns after save token into storage`() = runTest {
        val token = "token"
        val deps = makeDeps(backgroundScope, FakeTokenStorage(null))

        advanceUntilIdle()

        val initialToken = deps.provider.getTokenSync()

        assertThat(initialToken).isNull()

        val saveJob = deps.scope.launch {
            deps.storage.save(token)
        }

        saveJob.join()
        advanceUntilIdle()


        val savedToken = deps.provider.getTokenSync()

        assertThat(savedToken).isEqualTo(token)
    }


    @Test
    fun `getTokenSync returns after overwrites saved token into storage`() = runTest {
        val token = "token"
        val updateToken = "update_token"
        val deps = makeDeps(backgroundScope,  FakeTokenStorage(null))

        advanceUntilIdle()

        val initialToken = deps.provider.getTokenSync()

        assertThat(initialToken).isNull()

        val saveJob = deps.scope.launch {
            deps.storage.save(token)
        }
        val overwriteJob = deps.scope.launch {
            deps.storage.save(updateToken)
        }
        saveJob.join()
        overwriteJob.join()
        advanceUntilIdle()


        val savedToken = deps.provider.getTokenSync()

        assertThat(savedToken).isEqualTo(updateToken)

    }

    @Test
    fun `read already saved token from storage when token provider called`() = runTest {
        val token = "token"
        val deps = makeDeps(backgroundScope,    FakeTokenStorage(token))

        advanceUntilIdle()

        val savedToken = deps.provider.getTokenSync()

        assertThat(savedToken).isEqualTo(token)
    }

    @Test
    fun `observe the token should return null when token is cleared from datastore`() = runTest {
        val token = "token"
        val deps = makeDeps(backgroundScope, FakeTokenStorage(token))

        advanceUntilIdle()


        val savedToken = deps.provider.getTokenSync()

        assertThat(savedToken).isEqualTo(token)

        val launch = deps.scope.launch {
            deps.storage.clear()
        }

        launch.join()
        advanceUntilIdle()


        val readToken = deps.provider.getTokenSync()

        assertThat(readToken).isNull()


    }

    @Test
    fun `observe token is null when read throws`() = runTest {
        val storage = FailingReadFakeTokenStorage(null)

        val deps = makeDeps(this.backgroundScope, storage)
        advanceUntilIdle()
        assertThat(deps.provider.getTokenSync()).isNull()

        // emit later
        val saveJob = deps.scope.launch {
            storage.save("later")
        }
        saveJob.join()
        advanceUntilIdle()

        assertThat(deps.provider.getTokenSync()).isEqualTo("later")
    }

    private fun makeDeps(
        scope: CoroutineScope,
        storage: TokenStorage
    ): TestDependencies {

        return TestDependencies(storage, TokenProvider(storage, scope), scope)
    }

    private data class TestDependencies(
        val storage: TokenStorage,
        val provider: TokenProvider,
        val scope: CoroutineScope
    )
}