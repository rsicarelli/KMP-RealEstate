package com.rsicarelli.homehunt_kmm.domain.model

import com.rsicarelli.homehunt_kmm.domain.strategy.Filter
import com.rsicarelli.homehunt_kmm.domain.strategy.allFilters

data class SearchOption(
    val priceRange: Pair<Double, Double>,
    val surfaceRange: Pair<Int, Int>,
    val dormCount: Int,
    val bathCount: Int,
    val showSeen: Boolean,
    val longTermOnly: Boolean,
    val availableOnly: Boolean
) {
    private val filters: List<Filter> = allFilters

    fun applyFilter(properties: List<Property>): List<Property> {
        return properties.filter { property ->
            filters.all {
                it.applyFilter(this, property)
            }
        }
    }
}
