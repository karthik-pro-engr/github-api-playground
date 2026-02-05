package com.karthik.pro.engr.github.api.playground.presentation.repos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.devtools.AllVariantsPreview
import com.karthik.pro.engr.github.api.domain.model.Owner
import com.karthik.pro.engr.github.api.domain.model.Repo

@Composable
fun RepoListItem(modifier: Modifier = Modifier, repo: Repo) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            repo.fullName,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(5.dp)
        )
        repo.description?.let {
            Text(
                text = it, style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text("⭐ ${repo.stars}", modifier = modifier.padding(5.dp))
        Text(repo.language ?: "-", modifier = modifier.padding(5.dp))
        Divider()
    }

}

@AllVariantsPreview
@Composable
fun RepoListItemPreview() {
    RepoListItem(
        repo = Repo(
            id = 1,
            name = "admin-tools",
            fullName = "karthik-pro-engr/admin-tools",
            description = "Automates applying branch rulesets to new repositories.Automates applying branch rulesets to new repositories.Automates applying branch rulesets to new repositories.Automates applying branch rulesets to new repositories.Automates applying branch rulesets to new repositories.Automates applying branch rulesets to new repositories.Automates applying branch rulesets to new repositories.",
            htmlUrl = "https://github.com/karthik-pro-engr/admin-tools",
            language = "Shell",
            stars = 5,
            forks = 1,
            owner = Owner(
                login = "karthik-pro-engr",
                id = 101930095,
                avatarUrl = "https://avatars.githubusercontent.com/u/101930095?v=",
                htmlUrl = "https://github.com/karthik-pro-engr"
            )
        )
    )
}