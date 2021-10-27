package com.rsicarelli.homehunt.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rsicarelli.homehunt.ui.navigation.Screen

@Composable
fun NavigationOptions(selectedScreen: Screen, onScreenSelected: (screen: Screen) -> Unit) {
    Column(
        modifier = Modifier.padding(start = 72.dp, bottom = 16.dp),
    ) {
        listOf(Screen.Recommendations, Screen.Favourites, Screen.Map)
            .minus(selectedScreen)
            .forEach {
                Text(
                    text = stringResource(id = it.titleRes),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.clickable {
                        onScreenSelected(it)
                    })
            }
    }
}