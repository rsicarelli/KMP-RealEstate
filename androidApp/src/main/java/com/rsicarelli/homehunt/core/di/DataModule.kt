package com.rsicarelli.homehunt.core.di

import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rsicarelli.homehunt.data.datasource.FilterLocalDataSource
import com.rsicarelli.homehunt.data.datasource.FilterLocalDataSourceImpl
import com.rsicarelli.homehunt.data.datasource.FirestoreDataSource
import com.rsicarelli.homehunt.data.datasource.FirestoreDataSourceImpl
import com.rsicarelli.homehunt.data.repository.PropertyRepositoryOldImpl
import com.rsicarelli.homehunt.data.repository.UserRepositoryOldImpl
import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import com.rsicarelli.homehunt.domain.repository.UserRepository_Old
import com.rsicarelli.homehunt_kmm.data.cache.*
import com.rsicarelli.homehunt_kmm.data.network.PropertyService
import com.rsicarelli.homehunt_kmm.data.network.UserService
import com.rsicarelli.homehunt_kmm.data.repository.PropertyRepositoryImpl
import com.rsicarelli.homehunt_kmm.data.repository.SearchOptionRepositoryImpl
import com.rsicarelli.homehunt_kmm.data.repository.UserRepositoryImpl
import com.rsicarelli.homehunt_kmm.datasource.cache.HomeHuntDatabase
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository
import com.rsicarelli.homehunt_kmm.domain.repository.UserRepository
import com.russhwolf.settings.Settings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRecipeDatabase(context: Application): HomeHuntDatabase =
        HomeHuntDatabaseFactory(driverFactory = DriverFactory(context)).createDatabase()

    @Singleton
    @Provides
    fun providesUserCache(homeHuntDatabase: HomeHuntDatabase): UserCache =
        UserCacheImpl(homeHuntDatabase)

    @Singleton
    @Provides
    fun providesUserRepository(userCache: UserCache, userService: UserService): UserRepository {
        return UserRepositoryImpl(userCache, userService)
    }

    @Singleton
    @Provides
    fun providesPropertyCache(homeHuntDatabase: HomeHuntDatabase): PropertyCache =
        PropertyCacheImpl(homeHuntDatabase)

    @Singleton
    @Provides
    fun providesPropertyRepository(
        propertyCache: PropertyCache,
        propertyService: PropertyService
    ): PropertyRepository {
        return PropertyRepositoryImpl(propertyCache, propertyService)
    }

    @Provides
    @Singleton
    fun providesFirestoreDataSource(firestore: FirebaseFirestore): FirestoreDataSource =
        FirestoreDataSourceImpl(firestore, CoroutineScope(Dispatchers.IO))

    @Provides
    @Singleton
    fun providesUserRepository_OLD(firebaseAuth: FirebaseAuth): UserRepository_Old =
        UserRepositoryOldImpl(firebaseAuth)

    @Provides
    @Singleton
    fun providesPropertyRepository_OLD(firestoreDataSource: FirestoreDataSource): PropertyRepository_Old =
        PropertyRepositoryOldImpl(firestoreDataSource)

    @Provides
    @Singleton
    fun providesFilterLocalDataSource(sharedPreferences: SharedPreferences): FilterLocalDataSource =
        FilterLocalDataSourceImpl(sharedPreferences)

    @Provides
    @Singleton
    fun providesSearchOptionCache(settings: Settings): SearchOptionCache =
        SearchOptionCacheImpl(settings)

    @Provides
    @Singleton
    fun providesSearchOptionRepository(searchOptionCache: SearchOptionCache): SearchOptionRepository =
        SearchOptionRepositoryImpl(searchOptionCache)
}