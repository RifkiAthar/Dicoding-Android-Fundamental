package com.rifkiathar.githubuser.di.sharedPreferences.utils

import android.annotation.SuppressLint
import android.content.SharedPreferences
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

class SharedPreferencesData<T> (
    private val prefs: SharedPreferences,
    private val name: String,
    private val default: T
) {

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = with(prefs) {
        val res: Any = when (default) {
            is String -> getString(name, default).orEmpty()
            is Boolean -> getBoolean(name, default)
            is Int -> getInt(name, default)
            is Float -> getFloat(name, default)
            is Long -> getLong(name, default)
            is Double -> getString(name, default.toString())?.toDouble() as Double
            else -> throw  IllegalArgumentException("This type can't be saved into Preferences")
        }
        res as T
    }

    @SuppressLint("CommitPrefEdits")
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = with(prefs.edit()) {
        when (value) {
            is String -> putString(name, value)
            is Boolean -> putBoolean(name, value)
            is Int -> putInt(name, value)
            is Float -> putFloat(name, value)
            is Long -> putLong(name, value)
            is Double -> putString(name, value.toString())
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }
}