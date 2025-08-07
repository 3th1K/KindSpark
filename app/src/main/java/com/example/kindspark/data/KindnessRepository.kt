package com.example.kindspark.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class KindnessRepository(private val dao: KindnessPromptDao) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Pre-defined kindness prompts
    private val defaultPrompts = listOf(
        KindnessPrompt(1, "Compliment someone genuinely.", "social"),
        KindnessPrompt(2, "Call or text a family member to check on them.", "family"),
        KindnessPrompt(3, "Help a stranger today in any small way.", "community"),
        KindnessPrompt(4, "Leave a positive comment on social media.", "digital"),
        KindnessPrompt(5, "Pick up litter you see on the ground.", "environment"),
        KindnessPrompt(6, "Support a local business with kind words or a review.", "community"),
        KindnessPrompt(7, "Hold the door open for someone.", "courtesy"),
        KindnessPrompt(8, "Write a short thank-you note to someone.", "gratitude"),
        KindnessPrompt(9, "Donate clothes or items you no longer need.", "charity"),
        KindnessPrompt(10, "Smile at a stranger and say hello.", "social"),
        KindnessPrompt(11, "Let someone go ahead of you in line.", "courtesy"),
        KindnessPrompt(12, "Offer to help a coworker with their tasks.", "workplace"),
        KindnessPrompt(13, "Send an encouraging message to a friend.", "friendship"),
        KindnessPrompt(14, "Pay for someone's coffee or meal.", "generosity"),
        KindnessPrompt(15, "Volunteer for a local charity or cause.", "community"),
        KindnessPrompt(16, "Listen actively to someone who needs to talk.", "empathy"),
        KindnessPrompt(17, "Forgive someone who has wronged you.", "forgiveness"),
        KindnessPrompt(18, "Share your knowledge or skills with others.", "teaching"),
        KindnessPrompt(19, "Give a genuine compliment to a service worker.", "appreciation"),
        KindnessPrompt(20, "Plant a flower or tree in your community.", "environment")
    )

    suspend fun initializeDatabase() {
        val promptCount = dao.getPromptCount()
        if (promptCount == 0) {
            dao.insertPrompts(defaultPrompts)
        }
    }

    suspend fun getDailyPrompt(date: Date = Date()): KindnessPromptWithCompletion {
        val dateString = dateFormatter.format(date)

        // Check if there's already a completion for today
        val existingCompletion = dao.getCompletionByDate(dateString)

        if (existingCompletion != null) {
            // Return the prompt that was already completed today
            val prompt = dao.getPromptById(existingCompletion.promptId)
            return KindnessPromptWithCompletion(prompt!!, existingCompletion)
        }

        // Check if there's already a daily prompt selection for today
        val existingSelection = dao.getDailyPromptSelection(dateString)

        if (existingSelection != null) {
            // Return the previously selected prompt for today
            val prompt = dao.getPromptById(existingSelection.promptId)
            if (prompt != null) {
                return KindnessPromptWithCompletion(prompt, null)
            }
            // If prompt doesn't exist anymore, fall through to select a new one
        }

        // Get all prompts and select one randomly
        val allPrompts = dao.getAllPrompts()
        if (allPrompts.isEmpty()) {
            initializeDatabase()
            return getDailyPrompt(date)
        }

        // Get prompts that have been skipped today
        val skippedTodayIds = dao.getSkippedPromptIdsByDate(dateString)

        // Filter out skipped prompts
        val availablePrompts = allPrompts.filter { it.id !in skippedTodayIds }

        val selectedPrompts = if (availablePrompts.isNotEmpty()) {
            availablePrompts
        } else {
            // If all prompts have been skipped, use all prompts
            allPrompts
        }

        // Use date as seed for consistent daily prompt
        val seed = date.time
        val randomPrompt = selectedPrompts[Random(seed).nextInt(selectedPrompts.size)]

        // Save the daily prompt selection
        val dailySelection = DailyPromptSelection(
            date = dateString,
            promptId = randomPrompt.id
        )
        dao.insertDailyPromptSelection(dailySelection)

        return KindnessPromptWithCompletion(randomPrompt, null)
    }

    suspend fun markPromptAsCompleted(promptId: Int, date: Date = Date(), notes: String = ""): Long {
        val dateString = dateFormatter.format(date)
        val completion = KindnessCompletion(
            promptId = promptId,
            completedDate = dateString,
            notes = notes
        )
        return dao.insertCompletion(completion)
    }

    suspend fun toggleFavorite(completion: KindnessCompletion) {
        val updatedCompletion = completion.copy(isFavorite = !completion.isFavorite)
        dao.updateCompletion(updatedCompletion)
    }

    // History screen methods
    fun getAllCompletions(): Flow<List<KindnessCompletion>> {
        return dao.getAllCompletions()
    }

    fun getFavoriteCompletions(): Flow<List<KindnessCompletion>> {
        return dao.getFavoriteCompletions()
    }

    suspend fun getAllPrompts(): List<KindnessPrompt> {
        return dao.getAllPrompts()
    }

    suspend fun getPromptById(id: Int): KindnessPrompt? {
        return dao.getPromptById(id)
    }

    suspend fun updateCompletion(completion: KindnessCompletion) {
        dao.updateCompletion(completion)
    }

    suspend fun deleteCompletion(completion: KindnessCompletion) {
        dao.deleteCompletion(completion)
    }

    suspend fun getCurrentStreak(): Int {
        val today = Date()
        val calendar = Calendar.getInstance()
        var streak = 0

        calendar.time = today

        // Check backwards from today to find consecutive days
        while (true) {
            val dateString = dateFormatter.format(calendar.time)
            val completion = dao.getCompletionByDate(dateString)

            if (completion != null) {
                streak++
                calendar.add(Calendar.DAY_OF_MONTH, -1) // Go back one day
            } else {
                break
            }
        }

        return streak
    }

    suspend fun skipPrompt(promptId: Int, date: Date = Date(), reason: String = ""): Long {
        val dateString = dateFormatter.format(date)
        val skippedPrompt = SkippedPrompt(
            promptId = promptId,
            skippedDate = dateString,
            reason = reason
        )
        return dao.insertSkippedPrompt(skippedPrompt)
    }

    suspend fun getNextAvailablePrompt(date: Date = Date()): KindnessPromptWithCompletion {
        val dateString = dateFormatter.format(date)

        // Get all prompts
        val allPrompts = dao.getAllPrompts()
        if (allPrompts.isEmpty()) {
            initializeDatabase()
            return getNextAvailablePrompt(date)
        }

        // Get prompts that have been skipped today
        val skippedTodayIds = dao.getSkippedPromptIdsByDate(dateString)

        // Filter out skipped prompts
        val availablePrompts = allPrompts.filter { it.id !in skippedTodayIds }

        val selectedPrompt = if (availablePrompts.isEmpty()) {
            // If all prompts have been skipped, select from all prompts
            val seed = date.time
            allPrompts[Random(seed).nextInt(allPrompts.size)]
        } else {
            // Use date as seed for consistent selection, but add skip count to vary selection
            val seed = date.time + skippedTodayIds.size
            availablePrompts[Random(seed).nextInt(availablePrompts.size)]
        }

        // Update the daily prompt selection with the new prompt
        val dailySelection = DailyPromptSelection(
            date = dateString,
            promptId = selectedPrompt.id
        )
        dao.insertDailyPromptSelection(dailySelection)

        return KindnessPromptWithCompletion(selectedPrompt, null)
    }

    private fun getRandomPromptFromAll(allPrompts: List<KindnessPrompt>, date: Date): KindnessPromptWithCompletion {
        val seed = date.time
        val randomPrompt = allPrompts[Random(seed).nextInt(allPrompts.size)]
        return KindnessPromptWithCompletion(randomPrompt, null)
    }

    suspend fun cleanupOldSkippedPrompts(daysToKeep: Int = 7) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -daysToKeep)
        val cutoffDateString = dateFormatter.format(calendar.time)
        dao.deleteOldSkippedPrompts(cutoffDateString)

        // Also cleanup old daily prompt selections
        dao.deleteOldDailyPromptSelections(cutoffDateString)
    }

    // UserProgress and Streak Management
    suspend fun getUserProgress(): UserProgress {
        return dao.getUserProgress() ?: UserProgress(
            id = 1,
            currentStreak = 0,
            bestStreak = 0,
            lastCompletedDate = "",
            totalCompleted = 0,
            startDate = dateFormatter.format(Date())
        ).also {
            dao.insertOrUpdateUserProgress(it)
        }
    }

    suspend fun updateStreakOnCompletion(completionDate: Date = Date()): UserProgress {
        val currentProgress = getUserProgress()
        val completionDateString = dateFormatter.format(completionDate)

        // Don't update streak if already completed today
        if (currentProgress.lastCompletedDate == completionDateString) {
            return currentProgress
        }

        val newStreak = when {
            currentProgress.lastCompletedDate.isEmpty() -> 1 // First completion
            isConsecutiveDay(currentProgress.lastCompletedDate, completionDateString) -> currentProgress.currentStreak + 1
            else -> 1 // Streak broken, start over
        }

        val newBestStreak = maxOf(newStreak, currentProgress.bestStreak)
        val newTotalCompleted = currentProgress.totalCompleted + 1

        val updatedProgress = currentProgress.copy(
            currentStreak = newStreak,
            bestStreak = newBestStreak,
            lastCompletedDate = completionDateString,
            totalCompleted = newTotalCompleted
        )

        dao.insertOrUpdateUserProgress(updatedProgress)
        return updatedProgress
    }

    private fun isConsecutiveDay(lastCompletedDate: String, newCompletionDate: String): Boolean {
        if (lastCompletedDate.isEmpty()) return false

        try {
            val lastDate = dateFormatter.parse(lastCompletedDate) ?: return false
            val newDate = dateFormatter.parse(newCompletionDate) ?: return false

            val calendar = Calendar.getInstance()
            calendar.time = lastDate
            calendar.add(Calendar.DAY_OF_MONTH, 1) // Add one day to last date

            // Check if the new completion is exactly one day after the last completion
            val expectedDate = dateFormatter.format(calendar.time)
            return expectedDate == newCompletionDate
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun getStreakMilestones(currentStreak: Int): List<Int> {
        // Return milestone numbers that were just reached
        val milestones = listOf(3, 7, 14, 21, 30, 50, 100)
        return milestones.filter { milestone ->
            currentStreak == milestone
        }
    }
}
