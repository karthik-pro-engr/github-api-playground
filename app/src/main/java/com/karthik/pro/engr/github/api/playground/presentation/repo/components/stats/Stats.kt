package com.karthik.pro.engr.github.api.playground.presentation.repo.components.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.devtools.AllVariantsPreview
import com.karthik.pro.engr.github.api.playground.presentation.repo.RepoDetailTestTags.FORKS
import com.karthik.pro.engr.github.api.playground.presentation.repo.RepoDetailTestTags.STARS

@Composable
fun Stats(
    modifier: Modifier = Modifier,
    stars: String,
    forks: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp
        )
    ) {
        StatCard("Stars", stars, tag = STARS)
        StatCard("Forks", forks, FORKS)
    }
}

@Composable
fun StatCard(label: String, value: String, tag: String) {
    Card(
        modifier = Modifier
            .testTag(tag)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(value, fontWeight = FontWeight.Bold)
            Text(label)
        }
    }
}

@AllVariantsPreview
@Composable
private fun StatsPreview() {
    Stats(stars = "16.4k", forks = "24.5k")
}