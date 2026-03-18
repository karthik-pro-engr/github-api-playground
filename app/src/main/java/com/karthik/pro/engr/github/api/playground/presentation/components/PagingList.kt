package com.karthik.pro.engr.github.api.playground.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey

@Composable
fun <T : Any> PagingList(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
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