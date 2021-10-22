package com.rsicarelli.homehunt.presentation.login

data class LoginActions(
    val onDoLogin: () -> Unit,
    val onSignUp: () -> Unit,
    val onError: (Exception) -> Unit,
    val onShowMessageToUser: (String) -> Unit,
    val onNavigateSingleTop: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
    val onUserNameChanged: (String) -> Unit
)