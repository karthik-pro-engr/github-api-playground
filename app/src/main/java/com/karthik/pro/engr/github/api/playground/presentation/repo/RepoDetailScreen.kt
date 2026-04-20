package com.karthik.pro.engr.github.api.playground.presentation.repo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.karthik.pro.engr.devtools.AllVariantsPreview
import com.karthik.pro.engr.github.api.playground.presentation.components.ErrorUi
import com.karthik.pro.engr.github.api.playground.presentation.components.FullScreenLoader
import com.karthik.pro.engr.github.api.playground.presentation.components.SectionHeader
import com.karthik.pro.engr.github.api.playground.presentation.designsystem.Dimens
import com.karthik.pro.engr.github.api.playground.presentation.preview.fakeItems
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.header.Header
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.language.Language
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.releases.ReleaseItem
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.stats.Stats
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.topics.Topics
import com.karthik.pro.engr.github.api.playground.presentation.repo.model.RepoDetailItemUi
import com.karthik.pro.engr.github.api.playground.presentation.repo.model.key

@Composable
fun RepoDetailScreen(
    modifier: Modifier = Modifier,
    items: List<RepoDetailItemUi>,
    onRepoRetry: (String, String) -> Unit,
    onLanguageRetry: (String, String) -> Unit,
    onReleaseRetry: (String, String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = Dimens.large)
    ) {
        items(
            items = items,
            key = { item -> item.key() }
        ) { repoDetailItemUi ->

            when (repoDetailItemUi) {
                is RepoDetailItemUi.SectionTitle -> SectionHeader(repoDetailItemUi.title)

                is RepoDetailItemUi.HeaderError -> ErrorUi(
                    error = repoDetailItemUi.message,
                    onClick = { onRepoRetry("", "") }
                )

                RepoDetailItemUi.HeaderLoading -> FullScreenLoader()

                is RepoDetailItemUi.HeaderSuccess -> Header(
                    headerUi = repoDetailItemUi.data
                )

                is RepoDetailItemUi.Stats -> Stats(statsUi = repoDetailItemUi.statsUi)

                is RepoDetailItemUi.Topics -> Topics(topics = repoDetailItemUi.topics)

                is RepoDetailItemUi.LanguageError -> ErrorUi(
                    error = repoDetailItemUi.message,
                    onClick = { onLanguageRetry("", "") }
                )

                RepoDetailItemUi.LanguageLoading -> CircularProgressIndicator()

                is RepoDetailItemUi.LanguageSuccess -> {
                    Language(languagesList = repoDetailItemUi.data)
                }


                is RepoDetailItemUi.ReleaseError -> ErrorUi(
                    error = repoDetailItemUi.message,
                    onClick = { onReleaseRetry("", "") }
                )

                RepoDetailItemUi.ReleaseLoading -> CircularProgressIndicator()

                is RepoDetailItemUi.ReleaseSuccess -> {
                    ReleaseItem(repoDetailItemUi.data) { }
                }

            }
        }
    }
}

@AllVariantsPreview
@Composable
private fun RepoListScreenPreview() {
    RepoDetailScreen(
        items = fakeItems(),
        onRepoRetry = { owner, repoName -> },
        onLanguageRetry = { owner, repoName -> },
        onReleaseRetry = { owner, repoName -> },
    )

}


