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

    override suspend fun upVote(ratingInput: RatingInput) {
        propertyService.toggleRatings(ratingInput)
            .takeIf { success -> success }
            ?.let {
                propertyCache.updateRating(
                    isUpVoted = ratingInput.isUpVoted,
                    isDownVoted = false,
                    propertyId = ratingInput.propertyId
                )
            }
    }

    override suspend fun downVote(ratingInput: RatingInput) {
        propertyService.toggleRatings(ratingInput)
            .takeIf { success -> success }
            ?.let {
                propertyCache.updateRating(
                    isUpVoted = false,
                    isDownVoted = ratingInput.isUpVoted,
                    propertyId = ratingInput.propertyId
                )
            }
    }
}