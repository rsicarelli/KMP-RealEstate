package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rsicarelli.homehunt.presentation.components.Tag
import com.rsicarelli.homehunt.ui.theme.Size_Medium
import com.rsicarelli.homehunt.ui.theme.Size_Regular

@Composable
fun PropertyFeatures(characteristics: List<String>) {
    ChipVerticalGrid(
        spacing = Size_Medium,
        modifier = Modifier
            .padding(start = Size_Regular, end = Size_Regular, bottom = Size_Regular)
    ) {
        characteristics.forEach { word ->
            Tag(text = word)
        }
    }
}