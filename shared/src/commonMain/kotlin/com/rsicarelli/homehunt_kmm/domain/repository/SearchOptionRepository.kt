package com.rsicarelli.homehunt_kmm.domain.repository

import com.rsicarelli.homehunt_kmm.domain.model.SearchOption

interface SearchOptionRepository {
    fun get(): SearchOption
    fun save(searchOption: SearchOption)
}