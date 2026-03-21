package com.karthik.pro.engr.github.api.data.pagination

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.karthik.pro.engr.github.api.data.remote.api.GithubService
import com.karthik.pro.engr.github.api.data.remote.error.ApiException
import com.karthik.pro.engr.github.api.data.remote.error.ErrorParser
import com.karthik.pro.engr.github.api.data.remote.pagination.GithubPagingSource
import com.karthik.pro.engr.github.api.data.testutil.JsonReader
import com.karthik.pro.engr.github.api.domain.model.Repo
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException
import java.util.concurrent.TimeUnit


class GithubPagingSourceTest {
    lateinit var service: GithubService
    lateinit var mockWebServer: MockWebServer
    lateinit var errorParser: ErrorParser


    @Before
    fun setup() {

        mockWebServer = MockWebServer().apply {
            start()
        }


        val baseUrl = mockWebServer.url("/")

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .callTimeout(2, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(GithubService::class.java)
        errorParser = ErrorParser(retrofit)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(
        code: Int,
        body: String? = null,
        headers: Map<String, String> = emptyMap(),
        socketPolicy: SocketPolicy? = null
    ) {
        val response = MockResponse()
            .setResponseCode(code)

        body?.let { response.setBody(body) }
        socketPolicy?.let {
            response.setSocketPolicy(socketPolicy)
        }
        headers.forEach { (name, value) ->
            response.setHeader(name, value)
        }
        mockWebServer.enqueue(response)
    }

    private fun refreshParams(loadSize: Int = DEFAULT_PAGE_SIZE): PagingSource.LoadParams.Refresh<Int> =
        PagingSource.LoadParams.Refresh(
            key = null,
            loadSize = loadSize,
            placeholdersEnabled = false
        )

    private fun appendParams(key: Int): PagingSource.LoadParams.Append<Int> =
        PagingSource.LoadParams.Append(
            key = key,
            loadSize = DEFAULT_PAGE_SIZE,
            placeholdersEnabled = false
        )

    private fun createPagingSource(): GithubPagingSource {
        return GithubPagingSource(
            service = service,
            errorParser = errorParser,
            username = USERNAME
        )
    }

    private suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, Repo> {
        return createPagingSource().load(params)
    }

    private fun assertErrorResult(pageResult: PagingSource.LoadResult<Int, Repo>): PagingSource.LoadResult.Error<Int, Repo> {
        assertThat(pageResult).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        return pageResult as PagingSource.LoadResult.Error

    }

    private fun assertSuccessResult(pageResult: PagingSource.LoadResult<Int, Repo>): PagingSource.LoadResult.Page<Int, Repo> {
        assertThat(pageResult).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        return pageResult as PagingSource.LoadResult.Page

    }

    private fun assertRequestPage(page: Int) {
        val request = mockWebServer.takeRequest(1, TimeUnit.SECONDS)

        assertThat(request).isNotNull()

        val path = request!!.path ?: ""

        assertThat(path).contains("page=$page")
        assertThat(path).contains("per_page=$DEFAULT_PAGE_SIZE")

    }

    @Test
    fun `load returns success response for first page`() = runTest {

        enqueueResponse(
            code = 200,
            body = JsonReader.read(SUCCESS_PAGE1_RESPONSE_PATH),
            headers = mapOf(LINK_TAG to FIRST_PAGE_LINK_HEADER)
        )

        val pageResult = load(refreshParams())

        val result = assertSuccessResult(pageResult)

        assertThat(result.data.size).isEqualTo(DEFAULT_PAGE_SIZE)
        assertThat(result.prevKey).isNull()
        assertThat(result.nextKey).isEqualTo(2)

        assertRequestPage(page = 1)

    }


    @Test
    fun `load returns success response for append page`() = runTest {
        enqueueResponse(
            code = 200,
            body = JsonReader.read(SUCCESS_PAGE1_RESPONSE_PATH),
            headers = mapOf(LINK_TAG to SECOND_PAGE_LINK_HEADER)
        )

        val pageResult = load(appendParams(2))

        val result = assertSuccessResult(pageResult)
        assertThat(result.data.size).isEqualTo(DEFAULT_PAGE_SIZE)
        assertThat(result.prevKey).isEqualTo(1)
        assertThat(result.nextKey).isEqualTo(3)

        assertRequestPage(page = 2)

    }

    @Test
    fun `load returns success response for last page`() = runTest {
        enqueueResponse(
            code = 200,
            body = JsonReader.read(SUCCESS_LAST_PAGE_RESPONSE_PATH),
            headers = mapOf(LINK_TAG to LAST_PAGE_LINK_HEADER)
        )

        val pageResult = load(appendParams(19))


        val result = assertSuccessResult(pageResult)

        assertThat(result.nextKey).isNull()
        assertThat(result.prevKey).isEqualTo(18)

        assertRequestPage(page = 19)

    }

    @Test
    fun `load returns user not found error`() = runTest {
        enqueueResponse(
            code = 404,
            body = JsonReader.read(USER_NOT_FOUND_ERROR_PATH)
        )

        val pageResult = load(refreshParams())

        val error = assertErrorResult(pageResult)

        assertThat(error.throwable).isInstanceOf(ApiException::class.java)

        val apiException = error.throwable as? ApiException

        assertThat(apiException).isNotNull()
        assertThat(apiException!!.message).isEqualTo(NOT_FOUND)
        assertThat(apiException.code).isEqualTo(404)
    }

    @Test
    fun `load returns io exception when network disconnects`() = runTest {
        enqueueResponse(
            code = 200,
            socketPolicy = SocketPolicy.DISCONNECT_AT_START
        )

        val pageResult = load(
            refreshParams()
        )

        val error = assertErrorResult(pageResult)

        assertThat(error.throwable).isInstanceOf(java.io.IOException::class.java)

    }

    @Test
    fun `load returns io exception when timeout happens`() = runTest {
        enqueueResponse(
            code = 200,
            socketPolicy = SocketPolicy.NO_RESPONSE
        )
        val pageResult = load(refreshParams())

        val error = assertErrorResult(pageResult)

        assertThat(error.throwable).isInstanceOf(java.io.IOException::class.java)
    }

    @Test
    fun `load returns server error for 500 response`() = runTest {
        enqueueResponse(
            code = 500,
            body = JsonReader.read(INTERNAL_SERVER_ERROR_PATH)
        )

        val pageResult = load(refreshParams())

        val error = assertErrorResult(pageResult)

        assertThat(error.throwable).isInstanceOf(ApiException::class.java)
        val apiException = error.throwable as ApiException
        assertThat(apiException.message).isEqualTo(SERVER_ERROR)
        assertThat(apiException.code).isEqualTo(500)

    }

    @Test
    fun `load returns parsing error for malformed json`() = runTest {
        enqueueResponse(
            code = 200,
            body = MALFORMED_JSON
        )

        val pageResult = load(refreshParams())

        val error = assertErrorResult(pageResult)

        assertThat(error.throwable).isInstanceOf(EOFException::class.java)
    }

    @Test
    fun `load returns error for append page`() = runTest {
        enqueueResponse(
            code = 200,
            body = JsonReader.read(SUCCESS_PAGE1_RESPONSE_PATH),
            headers = mapOf(LINK_TAG to FIRST_PAGE_LINK_HEADER)
        )


        val firstPageResult = load(refreshParams())

        val firstPageLoadResult = assertSuccessResult(firstPageResult)

        assertThat(firstPageLoadResult.data.size).isEqualTo(DEFAULT_PAGE_SIZE)
        assertThat(firstPageLoadResult.prevKey).isNull()
        assertThat(firstPageLoadResult.nextKey).isEqualTo(2)
        enqueueResponse(
            code = 500,
            body = JsonReader.read(INTERNAL_SERVER_ERROR_PATH)
        )

        val secondPageResult = load(appendParams(2))

        val secondPageAppendResult = assertErrorResult(secondPageResult)

        assertThat(secondPageAppendResult.throwable).isInstanceOf(ApiException::class.java)
        val apiException = secondPageAppendResult.throwable as ApiException
        assertThat(apiException.message).isEqualTo(SERVER_ERROR)
        assertThat(apiException.code).isEqualTo(500)
    }


    @Test
    fun `load returns next page empty when no link header`() = runTest {
        enqueueResponse(
            code = 200,
            body = JsonReader.read(SUCCESS_PAGE1_RESPONSE_PATH)
        )


        val pageResult = load(refreshParams())
        val result = assertSuccessResult(pageResult)

        assertThat(result.nextKey).isNull()
        assertThat(result.prevKey).isNull()

        assertRequestPage(page = 1)
    }

    @Test
    fun `load returns success after previous failure on the next attempt`() = runTest {
        enqueueResponse(
            code = 500,
            body = JsonReader.read(INTERNAL_SERVER_ERROR_PATH),
            headers = mapOf(LINK_TAG to FIRST_PAGE_LINK_HEADER)
        )

        val pageErrorResult = load(refreshParams())
        val errorResult = assertErrorResult(pageErrorResult)
        assertThat(errorResult.throwable).isInstanceOf(ApiException::class.java)

        enqueueResponse(
            code = 200,
            body = JsonReader.read(SUCCESS_PAGE1_RESPONSE_PATH),
            headers = mapOf(
                LINK_TAG to FIRST_PAGE_LINK_HEADER
            )
        )


        val pageSuccessResult = load(refreshParams())
        val successResult = assertSuccessResult(pageSuccessResult)

        assertThat(successResult.data.size).isEqualTo(DEFAULT_PAGE_SIZE)
        assertThat(successResult.nextKey).isEqualTo(2)

        assertRequestPage(page = 1)

    }


    companion object {
        private const val SUCCESS_PAGE1_RESPONSE_PATH = "repo/success/repo_page1_success.json"
        private const val SUCCESS_LAST_PAGE_RESPONSE_PATH =
            "repo/success/repo_last_page_success.json"
        private const val INTERNAL_SERVER_ERROR_PATH = "repo/error/internal_server_error.json"
        private const val USER_NOT_FOUND_ERROR_PATH = "repo/error/user_not_found_error.json"

        private const val FIRST_PAGE_LINK_HEADER =
            "<https://api.github.com/user/66577/repos?per_page=8&page=2>; rel=\"next\", <https://api.github.com/user/66577/repos?per_page=8&page=19>; rel=\"last\""
        private const val LAST_PAGE_LINK_HEADER =
            "<https://api.github.com/user/66577/repos?per_page=8&page=18>; rel=\"prev\", <https://api.github.com/user/66577/repos?per_page=8&page=1>; rel=\"first\""

        private const val SECOND_PAGE_LINK_HEADER =
            "<https://api.github.com/user/66577/repos?per_page=8&page=1>; rel=\"prev\"," +
                    "<https://api.github.com/user/66577/repos?per_page=8&page=3>; rel=\"next\"," +
                    "<https://api.github.com/user/66577/repos?per_page=8&page=1>; rel=\"first\"," +
                    "<https://api.github.com/user/66577/repos?per_page=8&page=19>; rel=\"last\""

        private const val USERNAME = "JakeWharton"
        private const val LINK_TAG = "Link"
        private const val NOT_FOUND = "Not Found"
        private const val SERVER_ERROR = "Server Error"

        private const val MALFORMED_JSON = """[{ "id": 1, "name": """
        private const val DEFAULT_PAGE_SIZE = 8
    }

}