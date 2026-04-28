package com.karthik.pro.engr.github.api.playground.presentation.repo

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.karthik.pro.engr.github.api.core.testing.RepoFactory
import com.karthik.pro.engr.github.api.data.remote.mapper.toLanguageList
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.usecase.GetLanguageUseCase
import com.karthik.pro.engr.github.api.domain.usecase.GetReleasesUseCase
import com.karthik.pro.engr.github.api.domain.usecase.GetRepoDetailUseCase
import com.karthik.pro.engr.github.api.playground.presentation.repo.model.RepoDetailItemUi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

private suspend fun <T> ReceiveTurbine<T>.awaitUntil(
    condition: (T) -> Boolean
): T {
    while (true) {
        val item = awaitItem()
        if (condition(item)) return item
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class RepoDetailViewModelTest {

    private val repoUseCase = mockk<GetRepoDetailUseCase>()
    private val languageUseCase = mockk<GetLanguageUseCase>()
    private val releaseUseCase = mockk<GetReleasesUseCase>()

    private fun createViewModel() = RepoDetailViewModel(
        repoUseCase,
        languageUseCase,
        releaseUseCase
    )


    @Test
    fun `when repo loading then show header loading`() = runTest {
        coEvery { repoUseCase(any(), any()) } returns flow { awaitCancellation() }

        val vm = createViewModel()

        vm.uiItems.test {
            val items = awaitUntil {
                it.firstOrNull() is RepoDetailItemUi.HeaderLoading
            }

            assertThat(items.first())
                .isInstanceOf(RepoDetailItemUi.HeaderLoading::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `when repo error then show only header error`() = runTest {
        coEvery { repoUseCase(any(), any()) } returns flow {
            throw RuntimeException()
        }

        val vm = createViewModel()

        vm.uiItems.test {
            val items = awaitUntil {
                it.firstOrNull() is RepoDetailItemUi.HeaderError
            }

            assertThat(items.size).isEqualTo(1)
            assertThat(items.first())
                .isInstanceOf(RepoDetailItemUi.HeaderError::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `when repo success and others loading then show loading sections`() = runTest {
        coEvery { repoUseCase(any(), any()) } returns flowOf(RepoFactory.defaultRepo())
        coEvery { languageUseCase(any(), any()) } returns flow { awaitCancellation() }
        coEvery { releaseUseCase(any(), any()) } returns flow { awaitCancellation() }

        val vm = createViewModel()

        vm.uiItems.test {
            val items = awaitUntil {
                it.any { item -> item is RepoDetailItemUi.LanguageLoading } &&
                        it.any { item -> item is RepoDetailItemUi.ReleaseLoading }
            }

            assertThat(items.first())
                .isInstanceOf(RepoDetailItemUi.HeaderSuccess::class.java)

            assertThat(items.any { it is RepoDetailItemUi.LanguageLoading }).isTrue()
            assertThat(items.any { it is RepoDetailItemUi.ReleaseLoading }).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `when language fails then show language error`() = runTest {
        coEvery { repoUseCase(any(), any()) } returns flowOf(RepoFactory.defaultRepo())
        coEvery { languageUseCase(any(), any()) } returns flow {
            throw RuntimeException()
        }
        coEvery { releaseUseCase(any(), any()) } returns flow { awaitCancellation() }

        val vm = createViewModel()

        vm.uiItems.test {
            val items = awaitUntil {
                it.any { item -> item is RepoDetailItemUi.LanguageError }
            }

            assertThat(items.any { it is RepoDetailItemUi.LanguageError }).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ----------------------------
    // Release Error
    // ----------------------------
    @Test
    fun `when release fails then show release error`() = runTest {
        coEvery { repoUseCase(any(), any()) } returns flowOf(RepoFactory.defaultRepo())
        coEvery { languageUseCase(any(), any()) } returns flow { awaitCancellation() }
        coEvery { releaseUseCase(any(), any()) } returns flow {
            throw RuntimeException()
        }

        val vm = createViewModel()

        vm.uiItems.test {
            val items = awaitUntil {
                it.any { item -> item is RepoDetailItemUi.ReleaseError }
            }

            assertThat(items.any { it is RepoDetailItemUi.ReleaseError }).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `when all success then show full ui`() = runTest {
        coEvery { repoUseCase(any(), any()) } returns flowOf(RepoFactory.defaultRepo())
        coEvery { languageUseCase(any(), any()) } returns flowOf(RepoFactory.defaultLanguages().toLanguageList())
        coEvery { releaseUseCase(any(), any()) } returns flowOf(RepoFactory.defaultReleases())

        val vm = createViewModel()

        vm.uiItems.test {
            val items = awaitUntil {
                it.any { item -> item is RepoDetailItemUi.LanguageSuccess } &&
                        it.any { item -> item is RepoDetailItemUi.ReleaseSuccess }
            }

            assertThat(items.any { it is RepoDetailItemUi.LanguageSuccess }).isTrue()
            assertThat(items.any { it is RepoDetailItemUi.ReleaseSuccess }).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `when retry repo then state resets and reloads`() = runTest {

        val repoFlow = MutableSharedFlow<Repo>()

        coEvery { repoUseCase(any(), any()) } returns repoFlow
        coEvery { languageUseCase(any(), any()) } returns flow { awaitCancellation() }
        coEvery { releaseUseCase(any(), any()) } returns flow { awaitCancellation() }

        val vm = createViewModel()

        vm.uiItems.test {

            // First success
            repoFlow.emit(RepoFactory.defaultRepo())

            val first = awaitUntil {
                it.any { item -> item is RepoDetailItemUi.HeaderSuccess }
            }

            assertThat(first.any { it is RepoDetailItemUi.HeaderSuccess }).isTrue()

            // Retry
            vm.retryRepoDetail("", "")

            val loading = awaitUntil {
                it.firstOrNull() is RepoDetailItemUi.HeaderLoading
            }

            assertThat(loading.first())
                .isInstanceOf(RepoDetailItemUi.HeaderLoading::class.java)

            // Emit new repo
            repoFlow.emit(RepoFactory.defaultRepo().copy(name = "updated"))

            val second = awaitUntil {
                it.any { item -> item is RepoDetailItemUi.HeaderSuccess }
            }

            assertThat(second.any { it is RepoDetailItemUi.HeaderSuccess }).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }
}