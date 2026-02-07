package com.karthik.pro.engr.github.api.playground.presentation.repos

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.karthik.pro.engr.github.api.domain.model.Owner
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.result.Result
import com.karthik.pro.engr.github.api.domain.usecase.GetUserReposUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GithubRepoListViewModelTest {

    val useCase = mockk<GetUserReposUseCase>()
    val viewModel = GithubReposListViewModel(useCase)

    @Test
    fun `ui state updates with repo list when usecase returns data`() = runTest {
        val expected = Result.Success<List<Repo>>(
            listOf(
                Repo(
                    id = 1069192744,
                    name = "admin-tools",
                    fullName = "karthik-pro-engr/admin-tools",
                    description = "",
                    htmlUrl = "https://github.com/karthik-pro-engr",
                    language = "Shell",
                    stars = 0,
                    forks = 0,
                    owner = Owner(
                        login = "karthik-pro-engr",
                        id = 101930095,
                        avatarUrl = "https://avatars.githubusercontent.com/u/101930095?v=4",
                        htmlUrl = "https://github.com/karthik-pro-engr"
                    )
                )
            )
        )

        coEvery {
            useCase.invoke("karthik-pro-engr", 30, 1)
        } returns flow {
            emit(expected)
        }

        useCase("karthik-pro-engr").test {
            viewModel.loadRepos("karthik-pro-engr")
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isNotEmpty()
            assertThat((result).data[0].name).isEqualTo("admin-tools")
            cancelAndIgnoreRemainingEvents()
        }
    }


}