package com.example.kindspark.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kindspark.data.KindnessRepository
import com.example.kindspark.data.preferences.UserPreferencesManager
import com.example.kindspark.notifications.NotificationScheduler

class HomeViewModelFactory(
    private val repository: KindnessRepository,
    private val userPreferencesManager: UserPreferencesManager,
    private val notificationScheduler: NotificationScheduler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository, userPreferencesManager, notificationScheduler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
