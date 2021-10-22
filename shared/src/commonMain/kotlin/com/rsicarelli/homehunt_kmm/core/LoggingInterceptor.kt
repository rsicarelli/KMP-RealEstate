package com.rsicarelli.homehunt_kmm.core

import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.interceptor.ApolloRequestInterceptor
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import com.apollographql.apollo.interceptor.ApolloRequest
import com.apollographql.apollo.interceptor.ApolloResponse
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ApolloExperimental::class, ExperimentalTime::class)
internal class LoggingInterceptor : ApolloRequestInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val uuid = request.requestUuid
        val operation = request.operation.name().name()
        val variables = request.operation.variables().valueMap().toString()
        val (response, elapsed) = measureTimedValue {
            chain.proceed(request)
        }

        //TODO Improve with proper logger
        print("Request UUID: $uuid")
        print("Request Name: $operation")
        print("Request Variables: $variables")
        print("Request Time: ${elapsed.inWholeMilliseconds}")
        return response
    }
}