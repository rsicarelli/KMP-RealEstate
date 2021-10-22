package com.rsicarelli.homehunt_kmm.datasource.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.rsicarelli.homehunt_kmm.GetPropertiesQuery
import com.rsicarelli.homehunt_kmm.datasource.cache.mappers.toPropertyList
import com.rsicarelli.homehunt_kmm.domain.model.Property
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

interface PropertyService {
    suspend fun getProperties(): List<Property>?
    suspend fun getPropertyById(id: String): Property?
}

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class PropertyServiceImpl constructor(
    private val apolloClient: ApolloClient
) : PropertyService {
    override suspend fun getProperties(): List<Property>? {
        val response = apolloClient.query(GetPropertiesQuery()).execute().single()
        return response.data?.properties?.toPropertyList()
    }

    override suspend fun getPropertyById(id: String): Property? {
        TODO("Not yet implemented")
    }

}