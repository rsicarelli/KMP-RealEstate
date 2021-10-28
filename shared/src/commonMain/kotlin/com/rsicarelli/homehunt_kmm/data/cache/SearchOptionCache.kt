package com.rsicarelli.homehunt_kmm.data.cache

import com.rsicarelli.homehunt_kmm.data.cache.settings.*
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.russhwolf.settings.*
import kotlinx.coroutines.Job

@OptIn(ExperimentalSettingsApi::class)
interface SearchOptionCache {
    fun save(searchOption: SearchOption)
    fun get(): SearchOption
    fun listen(callback: () -> Unit): SettingsListener
}

class SearchOptionCacheImpl(
    private val settings: Settings
) : SearchOptionCache {

    var minPrice by DoubleSettingConfig(settings, Keys.MIN_PRICE, 0.0)
    var maxPrice by DoubleSettingConfig(settings, Keys.MAX_PRICE, 2000.0)
    var minSurface by IntSettingConfig(settings, Keys.MIN_SURFACE, 0)
    var maxSurface by IntSettingConfig(settings, Keys.MAX_SURFACE, 300)
    var dormCount by IntSettingConfig(settings, Keys.DORM_COUNT, 0)
    var bathCount by IntSettingConfig(settings, Keys.BATH_COUNT, 0)
    var showSeen by BooleanSettingConfig(settings, Keys.SHOW_SEEN, false)
    var longTermOnly by BooleanSettingConfig(settings, Keys.LONG_TERM_ONLY, false)
    var availableOnly by BooleanSettingConfig(settings, Keys.AVAILABLE_ONLY, false)
    var upVotedOnly by BooleanSettingConfig(settings, Keys.UP_VOTED_ONLY, true)

    override fun save(searchOption: SearchOption) {
        minPrice = searchOption.priceRange.first
        maxPrice = searchOption.priceRange.second
        minSurface = searchOption.surfaceRange.first
        maxSurface = searchOption.surfaceRange.second
        dormCount = searchOption.dormCount
        bathCount = searchOption.bathCount
        showSeen = searchOption.showSeen
        longTermOnly = searchOption.longTermOnly
        availableOnly = searchOption.availableOnly
        upVotedOnly = searchOption.upVotedOnly
    }

    override fun get(): SearchOption =
        SearchOption(
            priceRange = Pair(minPrice, maxPrice),
            surfaceRange = Pair(minSurface, maxSurface),
            dormCount = dormCount,
            bathCount = bathCount,
            showSeen = showSeen,
            longTermOnly = longTermOnly,
            availableOnly = availableOnly,
            upVotedOnly = upVotedOnly
        )

    @OptIn(ExperimentalSettingsApi::class)
    override fun listen(callback: () -> Unit): SettingsListener {
        return settings.listen(Keys.MIN_PRICE, callback)
    }

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