package com.rsicarelli.homehunt_kmm.domain.repository

import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.type.DownVoteInput
import com.rsicarelli.homehunt_kmm.type.UpVoteInput
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    fun fetchProperties(): Flow<List<Property>>
    fun getRecommendations(): Flow<List<Property>>
    fun getAll(): Flow<List<Property>>
    suspend fun getFavourites(): List<Property>
    suspend fun fetchFavourites(): List<Property>
    suspend fun getPropertyById(id: String): Property?
    suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput)
    suspend fun upVote(upVoteInput: UpVoteInput)
    suspend fun downVote(downVoteInput: DownVoteInput)
    val properties: Flow<List<Property>>
}