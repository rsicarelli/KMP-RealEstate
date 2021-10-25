package com.rsicarelli.homehunt_kmm.domain.model

import com.rsicarelli.homehunt_kmm.domain.strategy.PropertyFilter
import com.rsicarelli.homehunt_kmm.domain.strategy.allFilters

data class SearchOption(
    val priceRange: Pair<Double, Double>,
    val surfaceRange: Pair<Int, Int>,
    val dormCount: Int,
    val bathCount: Int,
    val showSeen: Boolean,
    val longTermOnly: Boolean,
    val availableOnly: Boolean,
    val upVotedOnly: Boolean = true
) {
    private val propertyFilters: List<PropertyFilter> = allFilters

    fun applyFilter(properties: List<Property>): List<Property> {
        return properties.filter { property ->
            propertyFilters.all {
                it.apply(this, property)
            }
        }
    }
}
