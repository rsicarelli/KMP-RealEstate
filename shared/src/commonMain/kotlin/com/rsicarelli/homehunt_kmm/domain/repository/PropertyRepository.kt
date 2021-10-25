package com.rsicarelli.homehunt_kmm.domain.repository

import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.type.RatingInput
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    fun getProperties(): Flow<List<Property>>
    suspend fun getPropertyById(id: String): Property?
    suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput)
    suspend fun upVote(ratingInput: RatingInput)
    suspend fun downVote(ratingInput: RatingInput)
}