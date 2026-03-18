package com.karthik.pro.engr.github.api.playground.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.karthik.pro.engr.github.api.playground.R

@Composable
fun PagingError(
    modifier: Modifier = Modifier,
    error: String,
    onClick: () -> Unit
) {
    Text(
        text = error,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = onClick) { Text(stringResource(R.string.retry)) }
}