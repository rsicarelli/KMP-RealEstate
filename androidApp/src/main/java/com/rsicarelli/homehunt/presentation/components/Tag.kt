package com.rsicarelli.homehunt.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Small
import com.rsicarelli.homehunt.ui.theme.Size_X_Small

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.surface,
    style: TextStyle = MaterialTheme.typography.body2
) {

    if (text.isEmpty()) return

    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.large),
        color = color
    ) {
        Text(
            text,
            style = style,
            modifier = Modifier
                .background(Color.Unspecified)
                .padding(
                    start = Size_Small,
                    end = Size_Small,
                    top = Size_X_Small,
                    bottom = Size_X_Small
                )
        )
    }
}

@Composable
@Preview
private fun TagPreview() {
    HomeHuntTheme {
        Tag(text = "A tag")
    }
}

@Composable
@Preview
private fun TagEmptyPreview() {
    HomeHuntTheme {
        Tag(text = "")
    }
}