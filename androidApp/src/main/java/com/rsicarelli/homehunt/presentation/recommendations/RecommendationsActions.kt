package com.rsicarelli.homehunt.presentation.recommendations

import com.rsicarelli.homehunt_kmm.domain.model.Property

internal data class RecommendationsActions(
    val onDownVote: (String) -> Unit,
    val onUpVote: (String) -> Unit,
    val onNavigate: (String) -> Unit,
    val onPropertyViewed: (Property) -> Unit,
)