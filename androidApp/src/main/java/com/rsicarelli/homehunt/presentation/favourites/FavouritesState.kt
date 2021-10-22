package com.rsicarelli.homehunt.presentation.favourites

import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Property

data class FavouritesState(
    val properties: List<Property> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isEmpty: Boolean = false
)