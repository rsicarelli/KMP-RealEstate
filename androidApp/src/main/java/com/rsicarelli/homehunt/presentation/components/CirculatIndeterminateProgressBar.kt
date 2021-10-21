package com.rsicarelli.homehunt.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.core.model.ProgressBarState
import com.rsicarelli.homehunt.core.model.isLoading
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

@Composable
fun CircularIndeterminateProgressBar(progressBarState: ProgressBarState) {
    if (!progressBarState.isLoading()) return

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
@Preview
private fun CircularIndeterminateProgressBarPreview() {
    HomeHuntTheme(isPreview = true) {
        CircularIndeterminateProgressBar(progressBarState = ProgressBarState.Loading)
    }
}