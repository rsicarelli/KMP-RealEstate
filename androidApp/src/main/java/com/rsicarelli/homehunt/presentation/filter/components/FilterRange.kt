package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.ui.theme.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColumnScope.FilterRange(
    title: String,
    range: ClosedFloatingPointRange<Float>,
    rangeText: String,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    Spacer(modifier = Modifier.height(Size_Regular))

    Text(
        text = title,
        style = MaterialTheme.typography.h6
    )

    Spacer(modifier = Modifier.height(Size_X_Small))

    Text(
        text = rangeText,
        style = MaterialTheme.typography.subtitle1
    )

    RangeSlider(
        modifier = Modifier.fillMaxWidth(),
        values = range,
        valueRange = valueRange,
        onValueChange = {
            onValueChange(it)
        })

    Spacer(modifier = Modifier.height(Size_Small))

    Divider(thickness = DividerSize)
}

@Composable
@Preview
private fun FilterRangePreview() {
    HomeHuntTheme(isPreview = true) {
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