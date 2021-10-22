package com.rsicarelli.homehunt.core.di

import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import com.rsicarelli.homehunt.domain.repository.UserRepository_Old
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
    fun providesSignInUseCase(userRepository: UserRepository) = SignInUseCase(userRepository)

    @Provides
    @Singleton
    fun providesSignUpUseCase(userRepository: UserRepository) = SignUpUseCase(userRepository)

    @Provides
    @Singleton
    fun providesVerifyUserCredentialsUseCase() = VerifyUserCredentialsUseCase()

    @Provides
    @Singleton
    fun providesIsLoggedInUseCase(userRepository: UserRepository) =
        IsLoggedInUseCase(userRepository)

    @Provides
    @Singleton
    fun providesGetSinglePropertyUseCase(propertiesRepositoryOld: PropertyRepository_Old) =
        GetSinglePropertyUseCase(propertiesRepositoryOld)

    @Provides
    @Singleton
    fun providesToggleFavouriteUseCase(propertiesRepositoryOld: PropertyRepository_Old) =
        ToggleFavouriteUseCase(propertiesRepositoryOld)

    @Provides
    @Singleton
    fun providesMarkAsViewedUseCase(
        propertiesRepositoryOld: PropertyRepository_Old,
        userRepositoryOld: UserRepository_Old
    ) = MarkAsViewedUseCase(propertiesRepositoryOld, userRepositoryOld)

    @Provides
    @Singleton
    fun providesGetFavouritedPropertiesUseCase(propertiesRepositoryOld: PropertyRepository_Old) =
        GetFavouritedPropertiesUseCase(propertiesRepositoryOld)

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
}