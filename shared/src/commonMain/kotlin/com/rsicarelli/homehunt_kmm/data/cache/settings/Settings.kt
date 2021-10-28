package com.rsicarelli.homehunt_kmm.data.cache.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener

@OptIn(ExperimentalSettingsApi::class, ExperimentalSettingsApi::class)
expect fun Settings.listen(key: String, callback: () -> Unit): SettingsListener