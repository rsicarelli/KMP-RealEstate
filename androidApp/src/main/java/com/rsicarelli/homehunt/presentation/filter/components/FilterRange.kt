package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.ui.theme.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterRange(
    title: String,
    range: ClosedFloatingPointRange<Float>,
    rangeText: String,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    Spacer(modifier = Modifier.height(Size_Regular))

    Text(
        text = rangeText,
        style = MaterialTheme.typography.subtitle1.copy(
            color = MaterialTheme.colors.primary
        )
    )

    RangeSlider(
        modifier = Modifier.fillMaxWidth(),
        values = range,
        valueRange = valueRange,
        onValueChange = {
            onValueChange(it)
        })

    Spacer(modifier = Modifier.height(Size_Small))

    Divider(
        thickness = DividerSize,
        color = MaterialTheme.colors.primary.copy(alpha = 0.12f),
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun FilterRangePreview() {
    HomeHuntTheme {
        val range = 400f..1500f
        val valueRange = 0f..3000f

        Column {
            FilterRange(
                title = "A filter range",
                range = range,
                valueRange = valueRange,
                rangeText = "${range.start.toInt()} - ${range.endInclusive.toInt()}",
                onValueChange = { }
            )
        }
    }
}