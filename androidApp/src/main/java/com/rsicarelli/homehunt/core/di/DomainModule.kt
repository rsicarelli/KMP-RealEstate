package com.rsicarelli.homehunt.core.di

import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository
import com.rsicarelli.homehunt_kmm.domain.repository.UserRepository
import com.rsicarelli.homehunt_kmm.domain.usecase.*
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
    fun providesSignInUseCase(userRepository: UserRepository): SignInUseCase =
        SignInUseCase(userRepository)

    @Provides
    @Singleton
    fun providesSignUpUseCase(userRepository: UserRepository): SignUpUseCase =
        SignUpUseCase(userRepository)

    @Provides
    @Singleton
    fun providesVerifyUserCredentialsUseCase(): VerifyUserCredentialsUseCase =
        VerifyUserCredentialsUseCase()

    @Provides
    @Singleton
    fun providesIsLoggedInUseCase(userRepository: UserRepository) =
        IsLoggedInUseCase(userRepository)

    @Provides
    @Singleton
    fun providesGetSinglePropertyUseCase(propertyRepository: PropertyRepository) =
        GetSinglePropertyUseCase(propertyRepository)

    @Provides
    @Singleton
    fun providesToggleFavouriteUseCase(propertyRepository: PropertyRepository) =
        ToggleFavouriteUseCase(propertyRepository)

    @Provides
    @Singleton
    fun providesMarkAsViewedUseCase(propertyRepository: PropertyRepository) =
        MarkAsViewedUseCase(propertyRepository)

    @Provides
    @Singleton
    fun providesGetFavouritedPropertiesUseCase(propertyRepository: PropertyRepository) =
        GetFavouritedPropertiesUseCase(propertyRepository)

    @Provides
    @Singleton
    fun providesFilterPropertiesUseCase(): FilterPropertiesUseCase =
        FilterPropertiesUseCase()

    @Provides
    @Singleton
    fun providesPreviewFilterResultUseCase(
        propertyRepository: PropertyRepository,
        filterPropertiesUseCase: FilterPropertiesUseCase
    ) = PreviewFilterResultUseCase(propertyRepository, filterPropertiesUseCase)

    @Provides
    @Singleton
    fun providesGetFilterPreferencesUseCase(searchOptionRepository: SearchOptionRepository) =
        GetSearchOptionSettings(searchOptionRepository)

    @Provides
    @Singleton
    fun providesSaveFilterPreferencesUseCase(searchOptionRepository: SearchOptionRepository) =
        SaveSearchOptionsUseCase(searchOptionRepository)

    @Provides
    fun providesGetFilteredPropertiesUseCase(
        propertyRepository: PropertyRepository,
        getFilterPreferences: GetSearchOptionSettings,
        filterProperties: FilterPropertiesUseCase,
    ) = GetFilteredPropertiesUseCase(
        propertyRepository = propertyRepository,
        getFilterPreferences = getFilterPreferences,
        filterProperties = filterProperties
    )

    @Provides
    fun provideGetRecommendationsUseCase(
        propertyRepository: PropertyRepository,
        getFilterPreferences: GetSearchOptionSettings,
        filterProperties: FilterPropertiesUseCase,
    ) = GetRecommendationsUseCase(
        propertyRepository = propertyRepository,
        getFilterPreferences = getFilterPreferences,
        filterProperties = filterProperties
    )
}