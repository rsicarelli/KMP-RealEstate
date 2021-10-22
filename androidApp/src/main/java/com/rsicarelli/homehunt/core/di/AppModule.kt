package com.rsicarelli.homehunt.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("home_hunt", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesSettings(sharedPreferences: SharedPreferences): Settings =
        AndroidSettings(sharedPreferences)

}