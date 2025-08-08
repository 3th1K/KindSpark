package com.example.kindspark.notifications

import android.content.Context
import androidx.work.*
import com.example.kindspark.data.preferences.UserPreferencesManager
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class NotificationScheduler(
    private val context: Context,
    private val preferencesManager: UserPreferencesManager
) {

    companion object {
        private const val KINDNESS_REMINDER_WORK = "kindness_reminder_work"
    }

    suspend fun scheduleNotifications() {
        val isEnabled = preferencesManager.notificationEnabled.first()
        val interval = preferencesManager.notificationInterval.first()

        // Cancel existing work
        WorkManager.getInstance(context).cancelUniqueWork(KINDNESS_REMINDER_WORK)

        // Only schedule if notifications are enabled
        if (!isEnabled) return

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .setRequiresCharging(false)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            interval.hours.toLong(),
            TimeUnit.HOURS,
            // Add flex time for better battery optimization
            15,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            KINDNESS_REMINDER_WORK,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancelNotifications() {
        WorkManager.getInstance(context).cancelUniqueWork(KINDNESS_REMINDER_WORK)
    }

    suspend fun rescheduleNotifications() {
        scheduleNotifications()
    }
}
