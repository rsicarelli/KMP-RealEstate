package com.rsicarelli.homehunt_kmm.data.cache.settings

import com.russhwolf.settings.*
import platform.Foundation.NSUserDefaults

val settings: Settings = AppleSettings(NSUserDefaults.standardUserDefaults)

@OptIn(ExperimentalSettingsApi::class)
actual fun Settings.listen(key: String, callback: () -> Unit): SettingsListener {
    return (this as AppleSettings).addListener(key, callback)
}