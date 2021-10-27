package com.rsicarelli.homehunt.presentation.discover

import com.rsicarelli.homehunt_kmm.domain.model.Property

internal data class DiscoverActions(
    val onDownVote: (String) -> Unit,
    val onUpVote: (String) -> Unit,
    val onNavigate: (String) -> Unit,
    val onPropertyViewed: (Property) -> Unit,
)