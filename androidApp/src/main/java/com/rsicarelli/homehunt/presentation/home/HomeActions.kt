package com.rsicarelli.homehunt.presentation.home

import com.rsicarelli.homehunt_kmm.domain.model.Property

internal data class HomeActions(
    val onToggleFavourite: (String, Boolean) -> Unit,
    val onNavigate: (String) -> Unit,
    val onPropertyViewed: (Property) -> Unit
)