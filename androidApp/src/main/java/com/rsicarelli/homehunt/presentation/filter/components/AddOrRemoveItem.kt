package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.ui.theme.Green_500
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Medium
import com.rsicarelli.homehunt.ui.theme.Size_Regular

@Composable
fun AddOrRemoveItem(
    text: String,
    value: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Spacer(modifier = Modifier.height(Size_Medium))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = text,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.W400,
                color = Color.White
            ),
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
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C)
private fun AddOrRemovePreview() {
    HomeHuntTheme {
        AddOrRemoveItem(
            text = "Hello world",
            value = 2,
            onIncrease = {},
            onDecrease = {}
        )
    }
}