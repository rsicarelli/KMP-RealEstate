package com.rsicarelli.homehunt.core.model

sealed class DataState<T> {

    data class Error<T>(
        val messageToUser: UiEvent.MessageToUser
    ) : DataState<T>()

    data class Data<T>(
        val data: T? = null
    ) : DataState<T>()

    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ) : DataState<T>()
}