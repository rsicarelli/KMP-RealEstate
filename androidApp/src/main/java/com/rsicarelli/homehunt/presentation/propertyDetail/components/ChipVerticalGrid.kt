package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.rsicarelli.homehunt.presentation.components.Tag
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Medium
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import utils.Fixtures

@Composable
fun ChipVerticalGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var currentRow = 0
        var currentOrigin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentOrigin.x > 0f && currentOrigin.x + placeable.width > constraints.maxWidth) {
                currentRow += 1
                currentOrigin =
                    currentOrigin.copy(x = 0, y = currentOrigin.y + placeable.height + spacingValue)
            }

            placeable to currentOrigin.also {
                currentOrigin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }

        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}

@Composable
@Preview
private fun ChipVerticalGroupSingleLinePreview() {
    val items = listOf(
        "AAC",
        "Elevator",
        "Balcony",
        "Gas"
    )

    HomeHuntTheme {
        ChipVerticalGrid(
            spacing = Size_Medium,
            modifier = Modifier
                .padding(Size_Regular)
        ) {
            items.forEach { word ->
                Tag(text = word)
            }
        }
    }
}

@Composable
@Preview
private fun ChipVerticalGroupMultilinePreview() {
    HomeHuntTheme {
        ChipVerticalGrid(
            spacing = Size_Medium,
            modifier = Modifier.padding(Size_Regular)
        ) {
            Fixtures.aProperty.characteristics.forEach { word ->
                Tag(text = word)
            }
        }
    }
}