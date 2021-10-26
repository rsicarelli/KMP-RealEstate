package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.annotation.ExperimentalCoilApi
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import com.rsicarelli.homehunt.ui.theme.MapSize
import com.rsicarelli.homehunt_kmm.domain.model.Location

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PropertyMap(
    location: Location,
    onMapClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Size_Regular, end = Size_Regular)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MapSize)
                .clip(shape = MaterialTheme.shapes.large)
        ) {
            StaticMapView(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMapClick() },
                location = location,
                isLiteMode = true
            )
        }

    }
}