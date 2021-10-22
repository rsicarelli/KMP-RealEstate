package com.rsicarelli.homehunt.core.di

import com.rsicarelli.homehunt_kmm.data.cache.UserCache
import com.rsicarelli.homehunt_kmm.data.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkService {

    @Singleton
    @Provides
    fun providesApolloClient(userCache: UserCache): ApolloProvider {
        return ApolloProvider(userCache)
    }

    @Singleton
    @Provides
    fun providesPropertyService(apolloProvider: ApolloProvider): PropertyService =
        PropertyServiceImpl(apolloProvider)

    @Singleton
    @Provides
    fun providesUserService(apolloProvider: ApolloProvider): UserService =
        UserServiceImpl(apolloProvider)

}