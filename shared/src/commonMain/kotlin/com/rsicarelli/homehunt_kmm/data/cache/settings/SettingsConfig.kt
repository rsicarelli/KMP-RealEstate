package com.rsicarelli.homehunt_kmm.data.cache.settings

import com.rsicarelli.homehunt_kmm.data.cache.SearchOptionCacheImpl
import com.russhwolf.settings.ExperimentalListener
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener
import kotlin.reflect.KProperty

sealed class SettingConfig<T>(
    val settings: Settings,
    val key: String,
    val defaultValue: T,
) {
    protected abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    protected abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)

    @ExperimentalSettingsApi
    private var listener: SettingsListener? = null

    fun remove() = settings.remove(key)
    fun exists(): Boolean = settings.hasKey(key)

    override fun toString() = key
}

class IntSettingConfig(
    private val settings: Settings,
    private val key: String,
    private val defaultValue: Int
) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int =
        settings.getInt(key, defaultValue)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) =
        settings.putInt(key, value)

}

class BooleanSettingConfig(
    private val settings: Settings,
    private val key: String,
    private val defaultValue: Boolean
) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
        settings.getBoolean(key, defaultValue)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) =
        settings.putBoolean(key, value)
}

class DoubleSettingConfig(
    private val settings: Settings,
    private val key: String,
    private val defaultValue: Double
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Double =
        settings.getDouble(key, defaultValue)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) =
        settings.putDouble(key, value)
}