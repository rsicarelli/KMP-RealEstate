package com.rsicarelli.homehunt.presentation.map

import com.rsicarelli.homehunt.domain.model.Property

data class MapActions(
    val onToggleFavourite: (String, Boolean) -> Unit,
    val onNavigate: (String) -> Unit,
    val onPropertyViewed: (Property) -> Unit,
    val onMarkerSelected: (Property) -> Unit,
    val onClusterClicked: (List<Property>) -> Unit,
    val onMapClick: () -> Unit
)