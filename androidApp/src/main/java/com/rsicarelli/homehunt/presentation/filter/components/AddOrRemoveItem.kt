package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Regular

@Composable
fun AddOrRemoveItem(
    text: String,
    value: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Spacer(modifier = Modifier.height(Size_Regular))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = text,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W400),
        )
        Counter(
            value,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            contentDescription = text
        )
    }
}

@Composable
@Preview
private fun AddOrRemovePreview() {
    HomeHuntTheme(isPreview = true) {
        AddOrRemoveItem(
            text = "Hello world",
            value = 2,
            onIncrease = {},
            onDecrease = {}
        )
    }
}