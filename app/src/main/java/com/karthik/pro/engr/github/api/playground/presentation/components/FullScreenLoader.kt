package com.karthik.pro.engr.github.api.playground.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.FULL_SCREEN_LOADER

@Composable
fun FullScreenLoader(modifier: Modifier = Modifier) {
    Box(
        Modifier
            .fillMaxSize()
            .testTag(FULL_SCREEN_LOADER), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}