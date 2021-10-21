package com.rsicarelli.homehunt.presentation.splash

import com.rsicarelli.homehunt.core.model.UiEvent

data class SplashState(
    val uiEvent: UiEvent = UiEvent.Idle
)