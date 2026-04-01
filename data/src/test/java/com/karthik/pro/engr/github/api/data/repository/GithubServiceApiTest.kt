package com.karthik.pro.engr.github.api.data.repository

import com.karthik.pro.engr.github.api.data.remote.api.GithubService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class GithubServiceApiTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var service: GithubService

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
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }

 /*   @Test
    fun `listUserRepos parses successful response`() = runTest {
        val body = FakeResponseLoader.load("repo/repo_success.json")
        mockWebServer.enqueue(MockResponse(200, body = body))

        val listUserRepos = service.listUserRepos("karthik-pro-engr", 30, 1)
        assertThat(listUserRepos).isNotNull()
        assertThat(listUserRepos).isNotEmpty()
        val firstItem = listUserRepos[0]
        assertThat(firstItem).isInstanceOf(GitHubRepoDto::class.java)
        assertThat(firstItem.name).isEqualTo("admin-tools")
        assertThat(firstItem.owner.login).isEqualTo("karthik-pro-engr")
    }*/
}