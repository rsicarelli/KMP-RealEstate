package com.rsicarelli.homehunt.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FilterFab(isScrollInProgress: Boolean, onClick: () -> Unit) {
    AnimatedVisibility(
        visible = !isScrollInProgress,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(shrinkTowards = Alignment.Top)
    ) {
        Box(
            modifier = Modifier.padding(end = Size_Small, bottom = Size_Small)
        ) {
            FloatingActionButton(
                onClick = onClick,
                elevation = FloatingActionButtonDefaults.elevation(ElevationSize),
                backgroundColor = rally_blue_700
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_filter),
                    contentDescription = stringResource(id = R.string.filter)
                )
            }
        }
    }
}

@Composable
@Preview
private fun FilterFabPreview() {
    HomeHuntTheme(isPreview = true) {
        FilterFab(isScrollInProgress = false, onClick = {})
    }
}