package com.rsicarelli.homehunt_kmm.datasource.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.interceptor.BearerTokenInterceptor
import com.apollographql.apollo.interceptor.TokenProvider
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.rsicarelli.homehunt_kmm.core.LoggingInterceptor
import com.rsicarelli.homehunt_kmm.datasource.cache.HomeHuntDatabase
import com.rsicarelli.homehunt_kmm.datasource.cache.UserCache
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val API_URL = "https://home-hunt-server.herokuapp.com/graphql"

class ApolloProvider(private val userCache: UserCache) : TokenProvider {
    @OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
    internal val apolloClient: ApolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = API_URL,
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json",
            ),
        ), interceptors = listOf(BearerTokenInterceptor(this), LoggingInterceptor())

    )

    override suspend fun currentToken(): String = userCache.getUser()?.token ?: ""

    override suspend fun refreshToken(previousToken: String): String = ""
}
