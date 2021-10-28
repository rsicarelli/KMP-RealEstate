package com.rsicarelli.homehunt_kmm.data.cache.settings

import com.russhwolf.settings.*

@OptIn(ExperimentalSettingsApi::class)
actual fun Settings.listen(key: String, callback: () -> Unit): SettingsListener {
    return (this as AndroidSettings).addListener(key, callback)
}