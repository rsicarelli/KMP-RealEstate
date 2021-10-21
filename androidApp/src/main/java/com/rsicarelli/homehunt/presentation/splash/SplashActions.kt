package com.rsicarelli.homehunt.presentation.splash

import com.rsicarelli.homehunt.core.model.UiText

data class SplashActions(
    val onAnimationEnded: () -> Unit,
    val onNavigateSingleTop: (String) -> Unit
)