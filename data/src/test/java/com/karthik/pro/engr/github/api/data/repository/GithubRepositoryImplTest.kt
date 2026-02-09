package com.karthik.pro.engr.github.api.data.repository

import com.google.common.truth.Truth.assertThat
import com.karthik.pro.engr.github.api.data.remote.GithubService
import com.karthik.pro.engr.github.api.data.util.FakeResponseLoader
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.result.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class GithubRepositoryImplTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var service: GithubService
    lateinit var repositoryImpl: GithubRepositoryImpl

    @Before
    fun setup() {

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/")

        val httpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(GithubService::class.java)

        repositoryImpl = GithubRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun `getUserRepos emits domain model list on success`() = runBlocking {
        val body = FakeResponseLoader.load("repo/repo_success.json")
        mockWebServer.enqueue(MockResponse(200, body = body))

        val userRepos = repositoryImpl.getUserRepos("karthik-pro-engr", 30, 1)
        val firstEmit = userRepos.first()
        assertThat(firstEmit).isInstanceOf(Result.Success::class.java)
        val data = (firstEmit as Result.Success).data
        assertThat(data).isNotNull()
        assertThat(data).isNotEmpty()
        assertThat(data[0]).isInstanceOf(Repo::class.java)
        assertThat(data[0].name).isEqualTo("admin-tools")

    }
}