package com.karthik.pro.engr.github.api.playground.presentation.repos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubReposListViewModel

@Composable
fun RepoListRoute(
    modifier: Modifier = Modifier,
    viewModel: GithubReposListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    RepoListScreen(
        modifier = modifier,
        state = state,
        onFetch = viewModel::loadRepos
    )
}