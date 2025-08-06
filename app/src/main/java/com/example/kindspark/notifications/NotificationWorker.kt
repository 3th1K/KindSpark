package com.example.kindspark.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.kindspark.MainActivity
import com.example.kindspark.R
import com.example.kindspark.data.KindnessDatabase
import com.example.kindspark.data.KindnessRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val CHANNEL_ID = "kindness_reminders"
        const val NOTIFICATION_ID = 1
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Get a random kindness prompt
            val database = KindnessDatabase.getDatabase(applicationContext)
            val repository = KindnessRepository(database.kindnessDao())

            // Initialize database if needed
            repository.initializeDatabase()

            // Get a random prompt for the notification
            val prompts = database.kindnessDao().getAllPrompts()
            if (prompts.isEmpty()) {
                return@withContext Result.failure()
            }

            val randomPrompt = prompts[Random.nextInt(prompts.size)]

            // Create and show notification
            createNotificationChannel()
            showNotification(randomPrompt.text)

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Kindness Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily reminders for acts of kindness"
                enableVibration(true)
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(promptText: String) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Time for Kindness ✨")
            .setContentText(promptText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(promptText))
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Using system icon instead of custom
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
