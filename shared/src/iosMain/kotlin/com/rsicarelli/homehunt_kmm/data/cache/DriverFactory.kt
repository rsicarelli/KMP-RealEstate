package com.rsicarelli.homehunt_kmm.data.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(HomeHuntDatabase.Schema, "homehunt.db")
    }
}