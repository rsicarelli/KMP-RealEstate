package com.rsicarelli.homehunt_kmm.data.network

import com.apollographql.apollo.api.ApolloExperimental
import com.rsicarelli.homehunt_kmm.*
import com.rsicarelli.homehunt_kmm.data.cache.mappers.toPropertyList
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.type.DownVoteInput
import com.rsicarelli.homehunt_kmm.type.UpVoteInput
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

interface PropertyService {
    suspend fun getAllProperties(): List<Property>?
    suspend fun getRecommendations(): List<Property>?
    suspend fun getPropertyById(id: String): Property?
    suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput): Boolean
    suspend fun upVote(upVoteInput: UpVoteInput): Boolean
    suspend fun downVote(downVoteInput: DownVoteInput): Boolean
    suspend fun fetchFavourites(): List<String>?
}

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class PropertyServiceImpl constructor(
    private val apolloProvider: ApolloProvider
) : PropertyService {
    override suspend fun getAllProperties(): List<Property>? {
        val response = apolloProvider.apolloClient.query(GetAllPropertiesQuery()).execute().single()
        return response.data?.properties?.toPropertyList()
    }

    override suspend fun getRecommendations(): List<Property>? {
        val response =
            apolloProvider.apolloClient.query(GetRecommendedPropertiesQuery()).execute().single()
        return response.data?.recommendedProperties?.toPropertyList()
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

    override suspend fun downVote(downVoteInput: DownVoteInput): Boolean {
        val response =
            apolloProvider.apolloClient.mutate(DownVotePropertyMutation(downVoteInput)).execute()
                .single()
        return !response.hasErrors()
    }

    override suspend fun fetchFavourites(): List<String>? {
        val response = apolloProvider.apolloClient.query(GetRatingsQuery()).execute().single()
        return response.data?.getRatings?.upVoted
    }

    override suspend fun upVote(upVoteInput: UpVoteInput): Boolean {
        val response =
            apolloProvider.apolloClient.mutate(UpVotePropertyMutation(upVoteInput)).execute()
                .single()
        return !response.hasErrors()
    }

}
