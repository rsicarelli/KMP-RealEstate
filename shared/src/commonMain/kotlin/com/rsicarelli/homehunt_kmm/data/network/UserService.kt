package com.rsicarelli.homehunt_kmm.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.rsicarelli.homehunt_kmm.SignInMutation
import com.rsicarelli.homehunt_kmm.SignUpMutation
import com.rsicarelli.homehunt_kmm.data.cache.mappers.toUser
import com.rsicarelli.homehunt_kmm.domain.model.User
import com.rsicarelli.homehunt_kmm.type.UserInput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

interface UserService {
    suspend fun signIn(userInput: UserInput): User
    suspend fun signUp(userInput: UserInput): User
}

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class UserServiceImpl constructor(
    private val apolloClient: ApolloClient
) : UserService {
    override suspend fun signIn(userInput: UserInput): User {
        val response = apolloClient.mutate(SignInMutation(userInput)).execute().single()
        response.data?.signIn?.let { data ->
            return data.toUser()
        } ?: error("Could not sign in")
    }

    override suspend fun signUp(userInput: UserInput): User {
        val response = apolloClient.mutate(SignUpMutation(userInput)).execute().single()
        response.data?.signUp?.let { data ->
            return data.toUser()
        } ?: error("Could not sign up")
    }

}
