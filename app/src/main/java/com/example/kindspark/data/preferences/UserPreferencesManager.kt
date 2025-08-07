package com.example.kindspark.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesManager(private val context: Context) {

    companion object {
        private val NOTIFICATION_INTERVAL_KEY = intPreferencesKey("notification_interval")
        private val NOTIFICATION_ENABLED_KEY = booleanPreferencesKey("notification_enabled")
        private val NOTIFICATION_SOUND_KEY = booleanPreferencesKey("notification_sound")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val SELECTED_THEME_KEY = stringPreferencesKey("selected_theme")
        private val CALMING_BACKGROUND_KEY = booleanPreferencesKey("calming_background")
        private val LOTTIE_ANIMATIONS_KEY = booleanPreferencesKey("lottie_animations")
    }

    enum class NotificationInterval(val hours: Int, val displayName: String) {
        ONCE_DAILY(24, "Once Daily"),
        EVERY_6_HOURS(6, "Every 6 Hours"),
        EVERY_3_HOURS(3, "Every 3 Hours"),
        EVERY_HOUR(1, "Every Hour")
    }

    enum class AppTheme(val displayName: String) {
        LIGHT("Light"),
        DARK("Dark"),
        CALM_OCEAN("Calm Ocean"),
        WARM_SUNSET("Warm Sunset"),
        SERENE_FOREST("Serene Forest"),
        MIDNIGHT_CALM("Midnight Calm")
    }

    val notificationInterval: Flow<NotificationInterval> = context.dataStore.data.map { preferences ->
        val hours = preferences[NOTIFICATION_INTERVAL_KEY] ?: 24
        NotificationInterval.values().find { it.hours == hours } ?: NotificationInterval.ONCE_DAILY
    }

    val notificationEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NOTIFICATION_ENABLED_KEY] ?: true
    }

    val notificationSound: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NOTIFICATION_SOUND_KEY] ?: true
    }

    val darkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val selectedTheme: Flow<AppTheme> = context.dataStore.data.map { preferences ->
        val themeName = preferences[SELECTED_THEME_KEY] ?: "LIGHT"
        AppTheme.values().find { it.name == themeName } ?: AppTheme.LIGHT
    }

    val calmingBackground: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[CALMING_BACKGROUND_KEY] ?: true
    }

    val lottieAnimations: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[LOTTIE_ANIMATIONS_KEY] ?: true
    }

    suspend fun updateNotificationInterval(interval: NotificationInterval) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_INTERVAL_KEY] = interval.hours
        }
    }

    suspend fun updateNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_ENABLED_KEY] = enabled
        }
    }

    suspend fun updateNotificationSound(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_SOUND_KEY] = enabled
        }
    }

    suspend fun updateDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun updateSelectedTheme(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_THEME_KEY] = theme.name
        }
    }

    suspend fun updateCalmingBackground(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[CALMING_BACKGROUND_KEY] = enabled
        }
    }

    suspend fun updateLottieAnimations(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOTTIE_ANIMATIONS_KEY] = enabled
        }
    }

    suspend fun resetToDefaults() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
