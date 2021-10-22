package com.rsicarelli.homehunt.presentation.login

import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.core.model.UiEvent

data class LoginState(
    val uiEvent: UiEvent = UiEvent.Idle,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val password: String = "",
    val userName: String = "",
    val invalidUsername: Boolean = false,
    val invalidPassword: Boolean = false,
)