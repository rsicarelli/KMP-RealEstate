package com.rsicarelli.homehunt.presentation.favourites

data class FavouriteActions(
    val onToggleFavourite: (String, Boolean) -> Unit,
    val onNavigate: (String) -> Unit,
)