package com.rsicarelli.homehunt.presentation.login

import com.rsicarelli.homehunt.core.model.UiText

data class LoginActions(
    val onDoLogin: () -> Unit,
    val onSignUp: () -> Unit,
    val onError: (Exception) -> Unit,
    val onShowMessageToUser: (UiText) -> Unit,
    val onNavigateSingleTop: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
    val onUserNameChanged: (String) -> Unit
)