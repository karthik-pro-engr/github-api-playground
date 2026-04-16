package com.karthik.pro.engr.github.api.playground.presentation.repo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.karthik.pro.engr.devtools.AllVariantsPreview
import com.karthik.pro.engr.github.api.domain.model.Owner
import com.karthik.pro.engr.github.api.playground.presentation.components.ErrorUi
import com.karthik.pro.engr.github.api.playground.presentation.components.FullScreenLoader
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.header.Header
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.language.Language
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.language.model.LanguageUi
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.releases.ReleaseItem
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.releases.model.ReleaseUi
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.stats.Stats
import com.karthik.pro.engr.github.api.playground.presentation.repo.model.RepoDetailUi
import com.karthik.pro.engr.github.api.playground.presentation.uistate.ListUiState

@Composable
fun RepoDetailScreen(
    modifier: Modifier = Modifier,
    uiState: RepoDetailUiState,
    languageUiState: ListUiState<LanguageUi>,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            is RepoDetailUiState.Error -> ErrorUi(error = uiState.message, onClick = onRetry)
            RepoDetailUiState.Loading -> FullScreenLoader()
            is RepoDetailUiState.Success -> {
                with(uiState.data) {
                    LazyColumn {
                        item {
                            Header(
                                owner = owner.name,
                                title = name,
                                description = description.orEmpty()
                            )
                        }

                        item {
                            Stats(
                                stars = stars,
                                forks = forks
                            )
                        }

                        item {
                            Language(
                                uiState = languageUiState
                            )
                        }

                        item {
                            Text("Releases", style = MaterialTheme.typography.headlineMedium)
                        }

                        items(
                            items = releases,
                            key = { it.id }
                        ) { release ->
                            ReleaseItem(release) { println() }
                        }
                    }
                }
            }
        }
    }

}

@AllVariantsPreview
@Composable
private fun RepoListScreenPreview() {
    RepoDetailScreen(
        languageUiState = ListUiState.Loading,
        onRetry = {},
        uiState = RepoDetailUiState.Success(
            RepoDetailUi(
                id = 1,
                name = "admin-tools",
                fullName = "karthik-pro-engr/admin-tools",
                description = "Automates applying branch rulesets to new repositories.",
                htmlUrl = "https://github.com/karthik-pro-engr/admin-tools",
                language = "Shell",
                stars = "5.6k",
                forks = "1.23k",
                languagesUrl = "https://api.github.com/repos/karthik-pro-engr/github-api-playground/languages",
                owner = Owner(
                    name = "karthik-pro-engr",
                    id = 101930095,
                    profilePictureUrl = "https://avatars.githubusercontent.com/u/101930095?v=",
                    htmlUrl = "https://github.com/karthik-pro-engr"
                ),
                releases = listOf(
                    ReleaseUi(1234, "v1.0.0", "Yesterday", true),
                    ReleaseUi(12345, "v1.0.0", "Yesterday", true),
                    ReleaseUi(12346, "v1.0.0", "Yesterday", true),
                    ReleaseUi(12347, "v1.0.0", "Yesterday", true),
                    ReleaseUi(12348, "v1.0.0", "Yesterday", true)
                )
            )
        )
    )

}


