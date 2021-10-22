package com.rsicarelli.homehunt_kmm.core.model

sealed class UiEvent {
    data class MessageToUser(val textId: Int) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    object Idle : UiEvent()
}