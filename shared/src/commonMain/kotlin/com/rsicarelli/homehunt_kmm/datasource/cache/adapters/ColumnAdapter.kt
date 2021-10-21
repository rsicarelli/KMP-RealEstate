package com.rsicarelli.homehunt_kmm.datasource.cache.adapters

import com.apollographql.apollo.api.internal.json.JsonReader
import com.rsicarelli.homehunt_kmm.domain.model.Location
import com.squareup.sqldelight.ColumnAdapter

val listOfStringsAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",")
        }

    override fun encode(value: List<String>) = value.joinToString(separator = ",")
}

val locationAdapter = object : ColumnAdapter<Location, String> {
    override fun decode(databaseValue: String): Location {
        val split = databaseValue.split(":")
        return Location(
            lat = split[0].toDouble(),
            lng = split[1].toDouble(),
            name = split[3],
            isApproximated = split[4].toBoolean(),
            isUnknown = split[5].toBoolean()
        )
    }

    override fun encode(value: Location): String {
        return "${value.lat}:" +
                "${value.lng}:" +
                "${value.name}:" +
                "${value.isApproximated}:" +
                "${value.isUnknown}"
    }
}