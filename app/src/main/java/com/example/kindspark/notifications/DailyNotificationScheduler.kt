package com.example.kindspark.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.kindspark.MainActivity
import com.example.kindspark.R
import com.example.kindspark.data.KindnessDatabase
import com.example.kindspark.data.KindnessRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class DailyNotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = KindnessDatabase.getDatabase(applicationContext)
            val repository = KindnessRepository(database.kindnessDao())

            // Check if today's prompt is already completed
            val dailyPrompt = repository.getDailyPrompt()

            if (dailyPrompt.completion == null) {
                // Send notification only if not completed
                createNotificationChannel()
                sendDailyNotification(dailyPrompt.prompt.text)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun createNotificationChannel() {
        val channelId = "daily_kindness_channel"
        val channelName = "Daily Kindness Reminders"
        val channelDescription = "Notifications for daily acts of kindness"

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun sendDailyNotification(promptText: String) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, "daily_kindness_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Time for Kindness! ✨")
            .setContentText(promptText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(promptText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}

class DailyNotificationScheduler(private val context: Context) {

    fun scheduleDailyNotification(hour: Int = 9, minute: Int = 0) {
        val workRequest = PeriodicWorkRequestBuilder<DailyNotificationWorker>(
            24, TimeUnit.HOURS
        ).setInitialDelay(calculateInitialDelay(hour, minute), TimeUnit.MILLISECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_kindness_notification",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancelDailyNotification() {
        WorkManager.getInstance(context).cancelUniqueWork("daily_kindness_notification")
    }

    private fun calculateInitialDelay(hour: Int, minute: Int): Long {
        val now = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }

        var targetTime = calendar.timeInMillis

        // If the target time has passed today, schedule for tomorrow
        if (targetTime <= now) {
            targetTime += 24 * 60 * 60 * 1000 // Add 24 hours
        }

        return targetTime - now
    }
}
