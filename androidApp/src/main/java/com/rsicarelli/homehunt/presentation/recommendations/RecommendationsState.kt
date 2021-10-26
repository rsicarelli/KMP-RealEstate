package com.rsicarelli.homehunt.presentation.home

import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Property

data class HomeState(
    val properties: List<Property> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Loading,
    val isEmpty: Boolean = false,
)