package com.example.kindspark.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface KindnessPromptDao {

    @Query("SELECT * FROM kindness_prompts")
    suspend fun getAllPrompts(): List<KindnessPrompt>

    @Query("SELECT * FROM kindness_prompts WHERE id = :id")
    suspend fun getPromptById(id: Int): KindnessPrompt?

    @Query("SELECT * FROM kindness_completions ORDER BY completedDate DESC")
    fun getAllCompletions(): Flow<List<KindnessCompletion>>

    @Query("SELECT * FROM kindness_completions WHERE completedDate = :date")
    suspend fun getCompletionByDate(date: String): KindnessCompletion?

    @Query("SELECT * FROM kindness_completions WHERE isFavorite = 1 ORDER BY completedDate DESC")
    fun getFavoriteCompletions(): Flow<List<KindnessCompletion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPrompts(prompts: List<KindnessPrompt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: KindnessCompletion): Long

    @Update
    suspend fun updateCompletion(completion: KindnessCompletion)

    @Delete
    suspend fun deleteCompletion(completion: KindnessCompletion)

    @Query("SELECT COUNT(*) FROM kindness_completions WHERE completedDate BETWEEN :startDate AND :endDate")
    suspend fun getCompletionCountBetweenDates(startDate: String, endDate: String): Int

    @Query("SELECT COUNT(*) FROM kindness_prompts")
    suspend fun getPromptCount(): Int

    // Skipped prompts methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkippedPrompt(skippedPrompt: SkippedPrompt): Long

    @Query("SELECT * FROM skipped_prompts WHERE skippedDate = :date")
    suspend fun getSkippedPromptsByDate(date: String): List<SkippedPrompt>

    @Query("SELECT promptId FROM skipped_prompts WHERE skippedDate = :date")
    suspend fun getSkippedPromptIdsByDate(date: String): List<Int>

    @Query("DELETE FROM skipped_prompts WHERE skippedDate < :cutoffDate")
    suspend fun deleteOldSkippedPrompts(cutoffDate: String)

    // UserProgress methods
    @Query("SELECT * FROM user_progress WHERE id = 1")
    suspend fun getUserProgress(): UserProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProgress(userProgress: UserProgress)

    @Query("UPDATE user_progress SET currentStreak = :streak, lastCompletedDate = :date WHERE id = 1")
    suspend fun updateStreak(streak: Int, date: String)

    @Query("UPDATE user_progress SET bestStreak = :bestStreak WHERE id = 1")
    suspend fun updateBestStreak(bestStreak: Int)
}
