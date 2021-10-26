package com.rsicarelli.homehunt.presentation.home

import com.rsicarelli.homehunt_kmm.domain.model.Property

internal data class HomeActions(
    val onDownVote: (String) -> Unit,
    val onUpVote: (String) -> Unit,
    val onNavigate: (String) -> Unit,
    val onPropertyViewed: (Property) -> Unit,
)