package com.rsicarelli.homehunt_kmm

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val API_URL = "https://home-hunt-server.herokuapp.com/graphql"

class ApolloProvider {
    @OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
    internal val apolloClient: ApolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = API_URL,
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json",
            ),
        )
    )
}
