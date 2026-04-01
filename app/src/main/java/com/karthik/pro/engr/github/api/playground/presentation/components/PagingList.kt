package com.karthik.pro.engr.github.api.playground.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.REPO_LIST

@Composable
fun <T : Any> PagingList(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag(REPO_LIST)
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.hashCode() }
        ) { index ->
            val repo = lazyPagingItems[index]
            repo?.let {
                itemContent(repo)
            }
        }

        item {
            PagingFooter(lazyPagingItems = lazyPagingItems)
        }
    }

}