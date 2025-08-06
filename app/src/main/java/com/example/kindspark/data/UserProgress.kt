package com.example.kindspark.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val id: Int = 1, // Single row for user progress
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val lastCompletedDate: String = "", // YYYY-MM-DD format
    val totalCompleted: Int = 0,
    val startDate: String = "" // When user first started using the app
)
