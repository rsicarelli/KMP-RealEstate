package com.rsicarelli.homehunt.domain.repository

import com.rsicarelli.homehunt_kmm.domain.model.Property
import kotlinx.coroutines.flow.Flow

interface PropertyRepository_Old {
    fun getActiveProperties(): Flow<List<Property>?>
    fun toggleFavourite(referenceId: String, isFavourited: Boolean)
    fun markAsViewed(referenceId: String, userId: String)
}