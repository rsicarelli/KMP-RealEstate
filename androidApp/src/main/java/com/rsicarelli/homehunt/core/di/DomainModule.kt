package com.rsicarelli.homehunt.core.di

import com.google.firebase.auth.FirebaseAuth
import com.rsicarelli.homehunt.data.datasource.FilterLocalDataSource
import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import com.rsicarelli.homehunt.domain.repository.UserRepository_Old
import com.rsicarelli.homehunt.domain.usecase.*
import com.rsicarelli.homehunt_kmm.domain.repository.UserRepository
import com.rsicarelli.homehunt_kmm.domain.usecase.IsLoggedInUseCase
import com.rsicarelli.homehunt_kmm.domain.usecase.SignInUseCase
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
        propertyRepositoryOld: PropertyRepository_Old,
        filterPropertiesUseCase: FilterPropertiesUseCase
    ) = PreviewFilterResultUseCase(propertyRepositoryOld, filterPropertiesUseCase)

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
        propertyRepositoryOld: PropertyRepository_Old,
        getFilterPreferences: GetFilterPreferencesUseCase,
        filterProperties: FilterPropertiesUseCase,
    ) = GetFilteredPropertiesUseCase(
        propertiesRepositoryOld = propertyRepositoryOld,
        getFilterPreferences = getFilterPreferences,
        filterProperties = filterProperties
    )
}