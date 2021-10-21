package com.rsicarelli.homehunt.presentation.login

import com.google.firebase.auth.AuthCredential
import com.rsicarelli.homehunt.core.model.UiEvent
import com.rsicarelli.homehunt.core.model.UiText

data class LoginActions(
    val onDoLogin: (AuthCredential) -> Unit,
    val onError: (Exception) -> Unit,
    val onShowMessageToUser: (UiText) -> Unit,
    val onNavigateSingleTop: (String) -> Unit,
)