package com.rsicarelli.homehunt_kmm.data.cache

import com.rsicarelli.homehunt_kmm.data.cache.mappers.toProperty
import com.rsicarelli.homehunt_kmm.data.cache.mappers.toPropertyList
import com.rsicarelli.homehunt_kmm.datasource.cache.HomeHuntDatabase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PropertyCache {
    fun getAll(): List<Property>
    fun get(propertyId: String): Property?
    fun saveAll(properties: List<Property>)
    fun updateVisibility(propertyId: String)
    fun upVote(propertyId: String)
    fun upVoteMany(ids: List<String>)
    fun downVote(propertyId: String)
    fun updateFavourites(ids: List<String>)
    val properties: Flow<List<Property>>
}

class PropertyCacheImpl(homeHuntDatabase: HomeHuntDatabase) : PropertyCache {
    private val queries = homeHuntDatabase.homeHuntQueries

    override val properties: Flow<List<Property>>
        get() = queries.selectAllProperties()
            .asFlow()
            .mapToList()
            .map {
                it.toPropertyList()
            }

    override fun getAll(): List<Property> =
        queries.selectAllProperties().executeAsList().toPropertyList()

    override fun get(propertyId: String): Property? =
        queries.selectPropertyById(propertyId).executeAsOneOrNull()?.toProperty()

    override fun saveAll(properties: List<Property>) {
        queries.transaction {
            queries.deleteAll()
            properties.forEach { insertProperty(it) }
        }
    }

    override fun updateVisibility(propertyId: String) {
        queries.updatePropertyVisibility(isViewed = true, _id = propertyId)
    }

    override fun upVote(propertyId: String) {
        queries.selectPropertyById(propertyId).executeAsOneOrNull()?.let {
            queries.updatePropertyRating(
                _id = propertyId,
                isUpVoted = !it.isUpVoted, //if upvoted, toggle
                isDownVoted = false
            )
        }
    }

    override fun upVoteMany(ids: List<String>) {
        queries.transaction {
            ids.forEach {
                upVote(it)
            }
        }
    }

    override fun updateFavourites(ids: List<String>) {
        with(queries) {
            transaction {
                resetFavourites()
                ids.forEach { propertyId ->
                    updatePropertyRating(
                        _id = propertyId,
                        isUpVoted = true,
                        isDownVoted = false
                    )
                }
            }
        }
    }

    override fun downVote(propertyId: String) {
        queries.selectPropertyById(propertyId).executeAsOneOrNull()?.let {
            queries.updatePropertyRating(
                _id = propertyId,
                isUpVoted = false,
                isDownVoted = true
            )
        }
    }

    private fun insertProperty(property: Property) {
        with(property) {
            queries.insertProperty(
                _id = _id,
                avatarUrl = avatarUrl,
                bathCount = bathCount,
                characteristics = characteristics,
                createdAt = avatarUrl,
                dormCount = dormCount,
                fullDescription = fullDescription,
                isActive = isActive,
                isViewed = isViewed,
                isUpVoted = isUpVoted,
                isDownVoted = isDownVoted,
                location = location,
                locationDescription = locationDescription,
                origin = origin,
                pdfUrl = pdfUrl,
                photoGalleryUrls = photoGalleryUrls,
                price = price,
                propertyUrl = propertyUrl,
                surface = surface,
                tag = tag,
                title = title,
                videoUrl = videoUrl
            )
        }
    }
}


