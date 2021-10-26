package com.rsicarelli.homehunt_kmm.data.repository

import com.rsicarelli.homehunt_kmm.data.cache.PropertyCache
import com.rsicarelli.homehunt_kmm.data.network.PropertyService
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.type.DownVoteInput
import com.rsicarelli.homehunt_kmm.type.UpVoteInput
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PropertyRepositoryImpl(
    private val propertyCache: PropertyCache,
    private val propertyService: PropertyService
) : PropertyRepository {
    override fun fetchProperties(): Flow<List<Property>> = flow {
        emit(propertyCache.getAll())
        propertyService.getAllProperties()?.let {
            propertyCache.saveAll(it)
            emit(it)
        }
    }.flowOn(Dispatchers.Default)

    override fun getRecommendations(): Flow<List<Property>> = flow {
        propertyService.getRecommendations()?.let {
            emit(it)
        }
    }.flowOn(Dispatchers.Default)

    override fun getAll(): Flow<List<Property>> = flow { emit(propertyCache.getAll()) }

    override suspend fun getFavourites(): List<Property> =
        propertyCache.getAll().filter { it.isUpVoted }

    override suspend fun fetchFavourites(): List<Property> {
        propertyService.fetchFavourites()?.let {
            propertyCache.updateFavourites(it)
        }.also { return getFavourites() }
    }

    override suspend fun getPropertyById(id: String): Property? = propertyCache.get(id)

    override suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput) {
        propertyService.markAsViewed(viewedPropertyInput)
            .takeIf { success -> success }
            ?.let {
                propertyCache.updateVisibility(viewedPropertyInput.propertyId)
            }
    }

    override suspend fun upVote(upVoteInput: UpVoteInput) {
        propertyService.upVote(upVoteInput)
            .takeIf { success -> success }
            ?.let {
                propertyCache.upVote(upVoteInput.propertyId)
            }
    }

    override suspend fun downVote(downVoteInput: DownVoteInput) {
        propertyService.downVote(downVoteInput)
            .takeIf { success -> success }
            ?.let {
                propertyCache.downVote(downVoteInput.propertyId)
            }
    }
}