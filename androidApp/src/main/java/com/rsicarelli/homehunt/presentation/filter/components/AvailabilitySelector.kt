package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Small

@Composable
fun AvailabilitySelector(
    isChecked: Boolean,
    onChange: (Boolean) -> Unit
) {
    CheckboxItem(
        title = stringResource(id = R.string.show_only_available),
        isChecked = isChecked,
        onCheckedChange = onChange
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun AvailabilitySelectorCheckedPreview() {
    HomeHuntTheme(isPreview = true) {
        Column {
            AvailabilitySelector(isChecked = true, onChange = {})
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun AvailabilitySelectorUncheckedPreview() {
    HomeHuntTheme(isPreview = true) {
        Column {
            AvailabilitySelector(isChecked = false, onChange = {})
        }
    }
}