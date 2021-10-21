package com.rsicarelli.homehunt.presentation.propertyDetail

data class PropertyDetailActions(
    val onOpenVideoPreview: () -> Unit,
    val onCloseVideoPreview: () -> Unit,
    val onOpenGallery: () -> Unit,
    val onCloseGallery: () -> Unit,
    val onToggleFavourite: (String, Boolean) -> Unit,
    val onNavigateUp: () -> Unit
)