package com.rsicarelli.homehunt_kmm.data.repository

import com.rsicarelli.homehunt_kmm.data.cache.PropertyCache
import com.rsicarelli.homehunt_kmm.data.network.PropertyService
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.type.RatingInput
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PropertyRepositoryImpl(
    private val propertyCache: PropertyCache,
    private val propertyService: PropertyService
) : PropertyRepository {
    override fun getProperties(): Flow<List<Property>> {
        return flow {
            emit(propertyCache.getAll())
            propertyService.getProperties()?.let {
                propertyCache.saveAll(it)
                emit(it)
            }
        }
    }

    override suspend fun getPropertyById(id: String): Property? = propertyCache.get(id)

    override suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput) {
        propertyService.markAsViewed(viewedPropertyInput)
            .takeIf { success -> success }
            ?.let {
                propertyCache.updateVisibility(viewedPropertyInput.propertyId)
            }
    }

    override suspend fun toggleRatings(ratingInput: RatingInput) {
        propertyService.toggleRatings(ratingInput)
            .takeIf { success -> success }
            ?.let {
                propertyCache.updateRating(ratingInput.isUpVoted, ratingInput.propertyId)
            }
    }
}