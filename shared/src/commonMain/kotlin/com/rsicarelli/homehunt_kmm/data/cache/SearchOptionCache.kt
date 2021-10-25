package com.rsicarelli.homehunt_kmm.data.cache

import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.russhwolf.settings.*

interface SearchOptionCache {
    fun save(searchOption: SearchOption)
    fun get(): SearchOption
}

class SearchOptionCacheImpl(
    private val settings: Settings
) : SearchOptionCache {
    override fun save(searchOption: SearchOption) {
        with(searchOption) {
            settings[Keys.MIN_PRICE] = priceRange.first
            settings[Keys.MAX_PRICE] = priceRange.second
            settings[Keys.MIN_SURFACE] = surfaceRange.first
            settings[Keys.MAX_SURFACE] = surfaceRange.second
            settings[Keys.DORM_COUNT] = dormCount
            settings[Keys.BATH_COUNT] = bathCount
            settings[Keys.SHOW_SEEN] = showSeen
            settings[Keys.LONG_TERM_ONLY] = longTermOnly
            settings[Keys.AVAILABLE_ONLY] = availableOnly
            settings[Keys.UP_VOTED_ONLY] = upVotedOnly
        }
    }

    override fun get(): SearchOption =
        SearchOption(
            dormCount = settings.getInt(Keys.DORM_COUNT, 0),
            bathCount = settings.getInt(Keys.BATH_COUNT, 0),
            showSeen = settings.getBoolean(Keys.SHOW_SEEN, false),
            longTermOnly = settings.getBoolean(Keys.LONG_TERM_ONLY, false),
            availableOnly = settings.getBoolean(Keys.AVAILABLE_ONLY, false),

            priceRange = Pair(
                settings.getDouble(Keys.MIN_PRICE, 0.0),
                settings.getDouble(Keys.MAX_PRICE, 2000.0)
            ),
            surfaceRange = Pair(
                settings.getInt(Keys.MIN_SURFACE, 0),
                settings.getInt(Keys.MAX_SURFACE, 300)
            ),
            upVotedOnly = settings.getBoolean(Keys.UP_VOTED_ONLY, true)
        )

    private object Keys {
        const val MIN_PRICE = "PREF_MIN_PRICE"
        const val MAX_PRICE = "PREF_MAX_PRICE"
        const val MIN_SURFACE = "PREF_MIN_SURFACE"
        const val MAX_SURFACE = "PREF_MAX_SURFACE"
        const val DORM_COUNT = "PREF_DORM_COUNT"
        const val BATH_COUNT = "PREF_BATH_COUNT"
        const val LONG_TERM_ONLY = "PREF_LONG_TERM_ONLY"
        const val SHOW_SEEN = "PREF_SHOW_SEEN"
        const val AVAILABLE_ONLY = "PREF_AVAILABLE_ONLY"
        const val UP_VOTED_ONLY = "PREF_UP_VOTED_ONLY"
    }
}