package com.rsicarelli.homehunt.domain.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rsicarelli.homehunt.domain.strategy.Filter
import com.rsicarelli.homehunt.domain.strategy.allFilters
import com.rsicarelli.homehunt_kmm.domain.model.Property

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
    val userId = Firebase.auth.uid //should not be here, refactor later

    fun applyFilter(properties: List<Property>): List<Property> {
        return properties.filter { property ->
            filters.all {
                it.applyFilter(this, property)
            }
        }
    }
}
