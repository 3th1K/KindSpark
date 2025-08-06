package com.example.kindspark.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "kindness_prompts")
data class KindnessPrompt(
    @PrimaryKey val id: Int,
    val text: String,
    val category: String = "general"
)

@Entity(tableName = "kindness_completions")
data class KindnessCompletion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val promptId: Int,
    val completedDate: String, // Using String to store LocalDate as YYYY-MM-DD
    val isFavorite: Boolean = false,
    val notes: String = ""
)

@Entity(tableName = "skipped_prompts")
data class SkippedPrompt(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val promptId: Int,
    val skippedDate: String, // Using String to store LocalDate as YYYY-MM-DD
    val reason: String = "" // Optional reason for skipping
)

data class KindnessPromptWithCompletion(
    val prompt: KindnessPrompt,
    val completion: KindnessCompletion?
)
