package com.rsicarelli.homehunt.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

@Composable
fun BackButton(
    modifier: Modifier,
    onBackClick: () -> Unit
) {
    IconButton(
        onClick = onBackClick
    ) {
        Icon(
            modifier = modifier,
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(id = R.string.go_back)
        )
    }
}

@Composable
@Preview
private fun BackButtonPreview() {
    HomeHuntTheme(isPreview = true) {
        BackButton(modifier = Modifier, onBackClick = {})
    }
}