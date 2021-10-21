package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

@Composable
fun BathSelector(
    bathCount: Int,
    onValueChanged: (Int) -> Unit,
) {
    AddOrRemoveItem(
        text = stringResource(id = R.string.bathrooms),
        value = bathCount,
        onIncrease = { onValueChanged(bathCount + 1) },
        onDecrease = { onValueChanged(bathCount - 1) }
    )
}

@Composable
@Preview
private fun BathSelectorPreview() {
    HomeHuntTheme(isPreview = true) {
        BathSelector(bathCount = 2, onValueChanged = {})
    }
}
