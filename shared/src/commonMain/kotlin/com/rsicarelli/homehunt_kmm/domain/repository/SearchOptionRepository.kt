package com.rsicarelli.homehunt_kmm.domain.repository

import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import kotlinx.coroutines.flow.Flow

interface SearchOptionRepository {
    fun get(): SearchOption
    fun save(searchOption: SearchOption)
    val searchOptions: Flow<SearchOption>
}