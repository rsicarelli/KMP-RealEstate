package com.rsicarelli.homehunt.presentation.map

import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.core.model.UiEvent
import com.rsicarelli.homehunt_kmm.domain.model.Property

data class MapState(
    val properties: List<Property> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val uiEvent: UiEvent = UiEvent.Idle,
    val showSinglePreview: Boolean = false,
    val showClusteredPreview: Boolean = false,
    val propertySnippet: List<Property> = emptyList(),
)
