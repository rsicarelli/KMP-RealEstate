package com.rsicarelli.homehunt.core.di

import com.google.firebase.auth.FirebaseAuth
import com.rsicarelli.homehunt.data.datasource.FilterLocalDataSource
import com.rsicarelli.homehunt.domain.repository.PropertyRepository
import com.rsicarelli.homehunt.domain.repository.UserRepository
import com.rsicarelli.homehunt.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun providesSignInUseCase(firebaseAuth: FirebaseAuth) = SignInUseCase(firebaseAuth)

    @Provides
    @Singleton
    fun providesIsLoggedInUseCase(userRepository: UserRepository) =
        IsLoggedInUseCase(userRepository)

    @Provides
    @Singleton
    fun providesGetSinglePropertyUseCase(propertiesRepository: PropertyRepository) =
        GetSinglePropertyUseCase(propertiesRepository)

    @Provides
    @Singleton
    fun providesToggleFavouriteUseCase(propertiesRepository: PropertyRepository) =
        ToggleFavouriteUseCase(propertiesRepository)

    @Provides
    @Singleton
    fun providesMarkAsViewedUseCase(
        propertiesRepository: PropertyRepository,
        userRepository: UserRepository
    ) = MarkAsViewedUseCase(propertiesRepository, userRepository)

    @Provides
    @Singleton
    fun providesGetFavouritedPropertiesUseCase(propertiesRepository: PropertyRepository) =
        GetFavouritedPropertiesUseCase(propertiesRepository)

    @Provides
    @Singleton
    fun providesFilterPropertiesUseCase() = FilterPropertiesUseCase()

    @Provides
    @Singleton
    fun providesPreviewFilterResultUseCase(
        propertyRepository: PropertyRepository,
        filterPropertiesUseCase: FilterPropertiesUseCase
    ) = PreviewFilterResultUseCase(propertyRepository, filterPropertiesUseCase)

    @Provides
    @Singleton
    fun providesGetFilterPreferencesUseCase(filterLocalDataSource: FilterLocalDataSource) =
        GetFilterPreferencesUseCase(filterLocalDataSource)

    @Provides
    @Singleton
    fun providesSaveFilterPreferencesUseCase(filterLocalDataSource: FilterLocalDataSource) =
        SaveFilterPreferencesUseCase(filterLocalDataSource)

    @Provides
    fun providesGetFilteredPropertiesUseCase(
        propertyRepository: PropertyRepository,
        getFilterPreferences: GetFilterPreferencesUseCase,
        filterProperties: FilterPropertiesUseCase,
    ) = GetFilteredPropertiesUseCase(
        propertiesRepository = propertyRepository,
        getFilterPreferences = getFilterPreferences,
        filterProperties = filterProperties
    )
}