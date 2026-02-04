package com.karthik.pro.engr.github.api.playground.presentation.repos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.github.api.domain.model.Repo

@Composable
fun RepoListItem(modifier: Modifier = Modifier, repo: Repo) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(repo.fullName)
        Text("⭐ ${repo.stars}")
        Text(repo.language ?: "-")
        Divider()
    }

}