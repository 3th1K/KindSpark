package com.example.kindspark.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kindspark.data.preferences.UserPreferencesManager
import com.example.kindspark.notifications.NotificationScheduler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SettingsUiState(
    val notificationEnabled: Boolean = true,
    val notificationInterval: UserPreferencesManager.NotificationInterval = UserPreferencesManager.NotificationInterval.ONCE_DAILY,
    val notificationSound: Boolean = true,
    val selectedTheme: UserPreferencesManager.AppTheme = UserPreferencesManager.AppTheme.LIGHT,
    val calmingBackground: Boolean = true,
    val lottieAnimations: Boolean = true,
    val isLoading: Boolean = false
)

class SettingsViewModel(
    private val preferencesManager: UserPreferencesManager,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            combine(
                preferencesManager.notificationEnabled,
                preferencesManager.notificationInterval,
                preferencesManager.notificationSound,
                preferencesManager.selectedTheme,
                preferencesManager.calmingBackground,
                preferencesManager.lottieAnimations
            ) { values ->
                val notificationEnabled = values[0] as Boolean
                val notificationInterval = values[1] as UserPreferencesManager.NotificationInterval
                val notificationSound = values[2] as Boolean
                val selectedTheme = values[3] as UserPreferencesManager.AppTheme
                val calmingBackground = values[4] as Boolean
                val lottieAnimations = values[5] as Boolean

                SettingsUiState(
                    notificationEnabled = notificationEnabled,
                    notificationInterval = notificationInterval,
                    notificationSound = notificationSound,
                    selectedTheme = selectedTheme,
                    calmingBackground = calmingBackground,
                    lottieAnimations = lottieAnimations,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun updateNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.updateNotificationEnabled(enabled)
            // Reschedule or cancel notifications based on the new setting
            if (enabled) {
                notificationScheduler.scheduleNotifications()
            } else {
                notificationScheduler.cancelNotifications()
            }
        }
    }

    fun updateNotificationInterval(interval: UserPreferencesManager.NotificationInterval) {
        viewModelScope.launch {
            preferencesManager.updateNotificationInterval(interval)
            // Reschedule notifications with the new interval
            if (_uiState.value.notificationEnabled) {
                notificationScheduler.rescheduleNotifications()
            }
        }
    }

    fun updateNotificationSound(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.updateNotificationSound(enabled)
        }
    }

    fun updateSelectedTheme(theme: UserPreferencesManager.AppTheme) {
        viewModelScope.launch {
            preferencesManager.updateSelectedTheme(theme)
        }
    }

    fun updateCalmingBackground(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.updateCalmingBackground(enabled)
        }
    }

    fun updateLottieAnimations(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.updateLottieAnimations(enabled)
        }
    }
}
