package com.rsicarelli.homehunt_kmm.datasource.cache

import com.rsicarelli.homehunt_kmm.datasource.cache.mappers.toProperty
import com.rsicarelli.homehunt_kmm.datasource.cache.mappers.toPropertyList
import com.rsicarelli.homehunt_kmm.domain.model.Property

interface PropertyCache {
    fun getAll(): List<Property>
    fun get(propertyId: String): Property?
}

class PropertyCacheImpl(homeHuntDatabase: HomeHuntDatabase) : PropertyCache {
    private val queries = homeHuntDatabase.homeHuntQueries

    override fun getAll(): List<Property> =
        queries.selectAllProperties().executeAsList().toPropertyList()

    override fun get(propertyId: String): Property? =
        queries.selectPropertyById(propertyId).executeAsOneOrNull()?.toProperty()

}


