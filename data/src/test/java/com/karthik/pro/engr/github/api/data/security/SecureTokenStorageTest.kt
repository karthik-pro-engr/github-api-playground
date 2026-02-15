package com.karthik.pro.engr.github.api.data.security


import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.crypto.tink.Aead
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Base64

@OptIn(ExperimentalCoroutinesApi::class)
class SecureTokenStorageTest {

    private val tokenKey = "encrypted_github_token"

    private data class TestDependencies(
        val aead: Aead,
        val kv: FakeKeyValueStore,
        val storage: SecureTokenStorage,
        val dispatcher: TestDispatcher
    )


    private fun makeStorageWithDispatcher(
        dispatcher: TestDispatcher,
        fakeAead: Aead = FakeAead()
    ): TestDependencies {
        val fakeKeyValueStore = FakeKeyValueStore()
        val fakeSecureTokenStorage = SecureTokenStorage(fakeKeyValueStore, fakeAead, dispatcher)
        return TestDependencies(fakeAead, fakeKeyValueStore, fakeSecureTokenStorage, dispatcher)
    }


    @Test
    fun `save should store encrypted base64 github token in datastore`() = runTest {
        val token = "ghp_test_token_123"

        val dispatcher = StandardTestDispatcher(testScheduler)

        with(makeStorageWithDispatcher(dispatcher)) {
            val saveJob = launch {
                storage.save(token)
            }
            advanceUntilIdle()
            saveJob.join()

            val read = kv.getString(tokenKey)!!
            val decode = Base64.getDecoder().decode(read)
            val decrypt = aead.decrypt(decode, null)

            assertThat(token).isEqualTo(String(decrypt))
        }

    }

    @Test
    fun `read should return the stored encrypted github token from datastore to normal token`() =
        runTest {

            val token = "ghp_test_token_123"

            val dispatcher = StandardTestDispatcher(testScheduler)
            with(makeStorageWithDispatcher(dispatcher)) {
                val encrypt = aead.encrypt(token.toByteArray(), null)

                val encoded = Base64.getEncoder().encodeToString(encrypt)
                kv.putString(tokenKey, encoded)
                val readJob = async {
                    storage.read()
                }
                advanceUntilIdle()

                val readToken = readJob.await()

                assertThat(token).isEqualTo(readToken)
            }

        }

    @Test
    fun `read should return null when no token stored datastore`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        with(makeStorageWithDispatcher(dispatcher)) {

            val result =   storage.read()


            assertThat(result).isNull()
        }

    }

    @Test
    fun `read should return null when token is empty`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        with(makeStorageWithDispatcher(dispatcher)) {
            kv.putString(tokenKey, "")

            val readToken =  storage.read()

            assertThat(readToken).isNull()
        }

    }

    @Test
    fun `observe when token is stored into DataStore`() = runTest {
        val token = "ghp_test_token_123"
        val dispatcher = StandardTestDispatcher(testScheduler)
        with(makeStorageWithDispatcher(dispatcher)) {
            storage.observe().test {
                assertThat(awaitItem()).isNull()
                val saveJob = launch {
                    storage.save(token)
                }

                advanceUntilIdle()
                saveJob.join()

                assertThat(awaitItem()).isEqualTo(token)
                cancelAndIgnoreRemainingEvents()
            }
        }


    }

    @Test
    fun `observe when token is removed from Datastore`() = runTest {
        val token = "ghp_test_token_123"
        val dispatcher = StandardTestDispatcher(testScheduler)
        with(makeStorageWithDispatcher(dispatcher)) {
            val saveJob = launch {
                storage.save(token)
            }

            advanceUntilIdle()
            saveJob.join()

            storage.observe().test {
                assertThat(awaitItem()).isEqualTo(token)
                val clearJob = launch {
                    storage.clear()
                }
                advanceUntilIdle()
                clearJob.join()

                assertThat(awaitItem()).isNull()
                cancelAndIgnoreRemainingEvents()
            }
        }

    }

    @Test
    fun `observe when stored value is broken`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        with(makeStorageWithDispatcher(dispatcher)) {
            kv.putString(tokenKey, "bad-value-corrupted-value")
            storage.observe().test {
                assertThat(awaitItem()).isNull()
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

@Test
fun `save should overwrite the previous token`() = runTest {
    val deps = makeStorageWithDispatcher(StandardTestDispatcher(testScheduler))

    with(deps) {
        val j1 = launch { storage.save("t1") }
        val j2 = launch { storage.save("t2") }

        advanceUntilIdle()
        j1.join()
        j2.join()

        assertThat(storage.read()).isEqualTo("t2")
    }
}

    @Test
    fun `read returns null when aead decrypt throws`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val failingAead: Aead = object : Aead {
            override fun encrypt(plaintext: ByteArray?, associatedData: ByteArray?) =
                throw RuntimeException("encrypt not supported")

            override fun decrypt(ciphertext: ByteArray?, associatedData: ByteArray?) =
                throw RuntimeException("decrypt failed")
        }
        with(makeStorageWithDispatcher(dispatcher, failingAead)) {

            kv.putString(
                tokenKey,
                Base64.getEncoder().encodeToString("dummy".toByteArray())
            )

            assertThat(storage.read()).isNull()
        }


    }
}