package com.rsicarelli.homehunt.core.model

sealed class UiEvent {
    data class MessageToUser(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    object Idle : UiEvent()
}