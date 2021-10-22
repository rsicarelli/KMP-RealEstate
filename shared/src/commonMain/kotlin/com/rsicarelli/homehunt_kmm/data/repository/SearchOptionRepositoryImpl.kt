package com.rsicarelli.homehunt_kmm.data.repository

import com.rsicarelli.homehunt_kmm.data.cache.SearchOptionCache
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository

class SearchOptionRepositoryImpl(
    private val searchOptionCache: SearchOptionCache
) : SearchOptionRepository {
    override fun get() = searchOptionCache.get()

    override fun save(searchOption: SearchOption) {
        searchOptionCache.save(searchOption)
    }
}