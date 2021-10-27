package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

private val valueRange = 0F..2000F

@Composable
fun PriceRange(
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    val rangeTextSuffix = if (range.endInclusive >= valueRange.endInclusive) "+" else ""

    FilterRange(
        title = stringResource(id = R.string.price_range),
        range = range,
        valueRange = valueRange,
        rangeText = "€${range.start.toInt()} - €${range.endInclusive.toInt()}$rangeTextSuffix",
        onValueChange = onValueChange
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun PriceRangeMaxValuePreview() {
    HomeHuntTheme() {
        Column {
            PriceRange(range = valueRange, onValueChange = {})
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun PriceRangeValuePreview() {
    HomeHuntTheme() {
        Column {
            PriceRange(range = 500F..1500F, onValueChange = {})
        }
    }
}

