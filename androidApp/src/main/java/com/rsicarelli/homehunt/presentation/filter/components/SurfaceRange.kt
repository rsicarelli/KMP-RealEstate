package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

private val valueRange = 0F..300F

@Composable
fun SurfaceRange(
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    val rangeTextSuffix =
        if (range.endInclusive >= valueRange.endInclusive) "+" else ""

    FilterRange(
        title = stringResource(id = R.string.surface_range),
        range = range,
        valueRange = valueRange,
        rangeText = "${range.start.toInt()}m² - ${range.endInclusive.toInt()}m²$rangeTextSuffix",
        onValueChange = onValueChange
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun SurfaceRangeMaxValuePreview() {
    HomeHuntTheme(isPreview = true) {
        Column {
            SurfaceRange(range = valueRange, onValueChange = {})
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun SurfaceRangeValuePreview() {
    HomeHuntTheme(isPreview = true) {
        Column {
            SurfaceRange(range = 50F..150F, onValueChange = {})
        }
    }
}