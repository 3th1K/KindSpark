package com.example.kindspark.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kindspark.data.preferences.UserPreferencesManager
import com.example.kindspark.notifications.NotificationScheduler

class SettingsViewModelFactory(
    private val preferencesManager: UserPreferencesManager,
    private val notificationScheduler: NotificationScheduler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(preferencesManager, notificationScheduler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
