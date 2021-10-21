package com.rsicarelli.homehunt.presentation.propertyDetail

import com.rsicarelli.homehunt.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Property

data class PropertyDetailState(
    val property: Property? = null,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val openGallery: Boolean = false,
    val openVideoPreview: Boolean = false
)