package com.rsicarelli.homehunt.ui.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val icon: ImageVector? = null,
    val painter: Painter? = null,
    val contentDescription: String? = null,
    val alertCount: Int? = null,
)
