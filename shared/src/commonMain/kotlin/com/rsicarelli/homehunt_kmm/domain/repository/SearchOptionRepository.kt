package com.rsicarelli.homehunt_kmm.domain.repository

import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.russhwolf.settings.SettingsListener
import kotlinx.coroutines.flow.Flow

interface SearchOptionRepository {
    fun get(): SearchOption
    fun save(searchOption: SearchOption)
    fun listen(callback: () -> Unit) : SettingsListener
    val searchOptions: Flow<SearchOption>
}