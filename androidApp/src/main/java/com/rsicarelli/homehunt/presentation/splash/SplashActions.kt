package com.rsicarelli.homehunt.presentation.splash

data class SplashActions(
    val onAnimationEnded: () -> Unit,
    val onNavigateSingleTop: (String) -> Unit
)