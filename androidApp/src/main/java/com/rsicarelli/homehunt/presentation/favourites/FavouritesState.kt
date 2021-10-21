package com.rsicarelli.homehunt.presentation.favourites

import com.rsicarelli.homehunt.core.model.ProgressBarState
import com.rsicarelli.homehunt.domain.model.Property

data class FavouritesState(
    val properties: List<Property> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isEmpty: Boolean = false
)