package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults.colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Small
import com.rsicarelli.homehunt.ui.theme.Size_X_Small

@Composable
fun CheckboxItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!isChecked)
            }
            .padding(top = Size_Small, bottom = Size_Small, end = Size_X_Small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = title,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W400),
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = colors(
                checkedColor = MaterialTheme.colors.primary
            )
        )
    }
}

@Composable
@Preview
private fun CheckboxItemCheckedPreview() {
    HomeHuntTheme(isPreview = true) {
        CheckboxItem(
            title = "Checked",
            isChecked = true,
            onCheckedChange = {}
        )
    }
}

@Composable
@Preview
private fun CheckboxItemUncheckedPreview() {
    HomeHuntTheme(isPreview = true) {
        CheckboxItem(
            title = "Unchecked",
            isChecked = false,
            onCheckedChange = {}
        )
    }
}