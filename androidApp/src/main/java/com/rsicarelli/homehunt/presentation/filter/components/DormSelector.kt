package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

@Composable
fun DormSelector(
    dormCount: Int,
    onValueChanged: (Int) -> Unit
) {
    AddOrRemoveItem(
        text = stringResource(id = R.string.bedrooms),
        value = dormCount,
        onIncrease = { onValueChanged(dormCount + 1) },
        onDecrease = { onValueChanged(dormCount - 1) }
    )
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun DormSelectorPreview() {
    HomeHuntTheme(isPreview = true) {
        DormSelector(dormCount = 4, onValueChanged = {})
    }
}
