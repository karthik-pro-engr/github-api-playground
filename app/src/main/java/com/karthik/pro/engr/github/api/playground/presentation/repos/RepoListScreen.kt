package com.karthik.pro.engr.github.api.playground.presentation.repos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.devtools.AllVariantsPreview
import com.karthik.pro.engr.github.api.domain.model.Owner
import com.karthik.pro.engr.github.api.domain.model.Repo

@Composable
fun RepoListScreen(
    modifier: Modifier = Modifier,
    state: UiState<List<Repo>>,
    onFetch: (String) -> Unit
) {

    var username by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = modifier.padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                label = { Text("GitHub username") }
            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = { onFetch(username) }) {
                Text("Fetch")
            }
        }

        Spacer(Modifier.height(16.dp))

        when (state) {
            UiState.Idle -> Text("Enter username")
            UiState.Loading -> CircularProgressIndicator(modifier = modifier.align(Alignment.CenterHorizontally))

            is UiState.Error ->
                Text(state.message, color = Color.Red)

            is UiState.Success ->
                if (state.data.isEmpty())
                    Text("No repos found", color = Color.Magenta)
                else
                    LazyColumn {
                        items(state.data) { RepoListItem(repo = it) }
                    }
        }
    }

}


@AllVariantsPreview
@Composable
private fun RepoListScreenPreview() {
    RepoListScreen(
        state = UiState.Success(
            data = listOf(
                Repo(
                    id = 1,
                    name = "admin-tools",
                    fullName = "karthik-pro-engr/admin-tools",
                    description = "Automates applying branch rulesets to new repositories.",
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
                ), Repo(
                    id = 2,
                    name = "admin-tools",
                    fullName = "karthik-pro-engr/admin-tools",
                    description = "Automates applying branch rulesets to new repositories.",
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
        )
    ) {
        println("Clicked")
    }
}