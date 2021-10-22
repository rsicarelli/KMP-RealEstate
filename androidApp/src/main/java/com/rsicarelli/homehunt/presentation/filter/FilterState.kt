package com.rsicarelli.homehunt.presentation.filter

import com.rsicarelli.homehunt_kmm.core.model.UiEvent
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption

data class FilterState(
    val uiEvent: UiEvent = UiEvent.Idle,
    val priceRange: ClosedFloatingPointRange<Float> = 0f..2000F,
    val surfaceRange: ClosedFloatingPointRange<Float> = 0f..300f,
    val dormCount: Int = 0,
    val bathCount: Int = 0,
    val showSeen: Boolean = false,
    val longTermOnly: Boolean = false,
    val availableOnly: Boolean = false,
    val previewResultCount: Int? = null,
) {

    fun toSearchOption() = SearchOption(
        priceRange = Pair(priceRange.start.toDouble(), priceRange.endInclusive.toDouble()),
        surfaceRange = Pair(surfaceRange.start.toInt(), surfaceRange.endInclusive.toInt()),
        dormCount = dormCount,
        bathCount = bathCount,
        showSeen = showSeen,
        longTermOnly = longTermOnly,
        availableOnly = availableOnly,
    )
}

internal fun SearchOption.toState(): FilterState = FilterState(
    priceRange = priceRange.first.toFloat()..priceRange.second.toFloat(),
    surfaceRange = surfaceRange.first.toFloat()..surfaceRange.second.toFloat(),
    bathCount = bathCount,
    dormCount = dormCount,
    showSeen = showSeen,
    longTermOnly = longTermOnly,
    availableOnly = availableOnly
)