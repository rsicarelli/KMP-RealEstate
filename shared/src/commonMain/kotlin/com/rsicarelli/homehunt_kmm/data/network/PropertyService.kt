package com.rsicarelli.homehunt_kmm.data.network

import com.apollographql.apollo.api.ApolloExperimental
import com.rsicarelli.homehunt_kmm.GetPropertiesQuery
import com.rsicarelli.homehunt_kmm.MarkAsViewedMutation
import com.rsicarelli.homehunt_kmm.ToggleRatingsMutation
import com.rsicarelli.homehunt_kmm.data.cache.mappers.toPropertyList
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.type.RatingInput
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

interface PropertyService {
    suspend fun getProperties(): List<Property>?
    suspend fun getPropertyById(id: String): Property?
    suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput): Boolean
    suspend fun toggleRatings(ratingInput: RatingInput): Boolean
}

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class PropertyServiceImpl constructor(
    private val apolloProvider: ApolloProvider
) : PropertyService {
    override suspend fun getProperties(): List<Property>? {
        val response = apolloProvider.apolloClient.query(GetPropertiesQuery()).execute().single()
        return response.data?.properties?.toPropertyList()
    }

    override suspend fun getPropertyById(id: String): Property? {
        TODO("Not yet implemented")
    }

    override suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput): Boolean {
        val response =
            apolloProvider.apolloClient.mutate(MarkAsViewedMutation(viewedPropertyInput)).execute()
                .single()
        return response.data?.markAsViewed?.propertyId?.let { true } ?: false
    }

    override suspend fun toggleRatings(ratingInput: RatingInput): Boolean {
        val response =
            apolloProvider.apolloClient.mutate(ToggleRatingsMutation(ratingInput)).execute()
                .single()
        return response.data?.toggleRating?.propertyId?.let { true } ?: false
    }

}