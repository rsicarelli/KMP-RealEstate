package com.rsicarelli.homehunt.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = PrimaryVariant,
    onPrimary = OnSecondary,
    secondary = Secondary,
    secondaryVariant = SecondaryVariant,
    onSecondary = OnSecondary,
    error = RedErrorLight,
    onError = OnError,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
)


@Composable
fun HomeHuntTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isPreview: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = EczarTypography,
        shapes = AppShapes,
        content = {
            if (isPreview) {
                Surface { content() }
            } else {
                content()
            }
        }
    )
}
