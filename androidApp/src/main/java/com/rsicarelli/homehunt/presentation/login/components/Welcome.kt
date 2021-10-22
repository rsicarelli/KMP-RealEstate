package com.rsicarelli.homehunt.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_2X_Large
import com.rsicarelli.homehunt.ui.theme.Size_Large
import com.rsicarelli.homehunt.ui.theme.Size_Regular

@Composable
fun ColumnScope.Welcome() {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(id = R.string.welcome),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4,
        color = Color.White
    )
}

@Composable
@Preview
private fun WelcomePreview() {
    HomeHuntTheme(isPreview = true) {
        Column {
            Welcome()
        }
    }
}