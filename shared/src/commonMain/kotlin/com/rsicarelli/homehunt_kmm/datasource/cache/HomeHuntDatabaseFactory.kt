package com.rsicarelli.homehunt_kmm.datasource.cache

import com.rsicarelli.homehunt_kmm.datasource.cache.adapters.listOfStringsAdapter
import com.rsicarelli.homehunt_kmm.datasource.cache.adapters.locationAdapter
import com.rsicarelli.homehuntkmm.datasource.cache._Property
import com.squareup.sqldelight.db.SqlDriver

class HomeHuntDatabaseFactory(
    private val driverFactory: DriverFactory
) {
    fun createDatabase(): HomeHuntDatabase {
        return HomeHuntDatabase(
            driverFactory.createDriver(), _PropertyAdapter = _Property.Adapter(
                characteristicsAdapter = listOfStringsAdapter,
                locationAdapter = locationAdapter,
                photoGalleryUrlsAdapter = listOfStringsAdapter
            )
        )
    }
}

expect class DriverFactory {
    fun createDriver(): SqlDriver
}