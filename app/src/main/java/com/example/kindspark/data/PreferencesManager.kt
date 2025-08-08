package com.example.kindspark.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class PreferencesManager(private val context: Context) {

    companion object {
        private val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        private val NOTIFICATION_HOUR = intPreferencesKey("notification_hour")
        private val NOTIFICATION_MINUTE = intPreferencesKey("notification_minute")
        private val NOTIFICATION_SOUND = booleanPreferencesKey("notification_sound")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    val notificationEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[NOTIFICATION_ENABLED] ?: true }

    val notificationHour: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[NOTIFICATION_HOUR] ?: 9 }

    val notificationMinute: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[NOTIFICATION_MINUTE] ?: 0 }

    val notificationSound: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[NOTIFICATION_SOUND] ?: true }

    val darkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[DARK_MODE] ?: false }

    suspend fun updateNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_ENABLED] = enabled
        }
    }

    suspend fun updateNotificationTime(hour: Int, minute: Int) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_HOUR] = hour
            preferences[NOTIFICATION_MINUTE] = minute
        }
    }

    suspend fun updateNotificationSound(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_SOUND] = enabled
        }
    }

    suspend fun updateDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = enabled
        }
    }
}
