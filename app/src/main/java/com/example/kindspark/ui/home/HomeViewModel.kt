package com.example.kindspark.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kindspark.data.KindnessCompletion
import com.example.kindspark.data.KindnessPromptWithCompletion
import com.example.kindspark.data.KindnessRepository
import com.example.kindspark.data.preferences.UserPreferencesManager
import com.example.kindspark.data.UserProgress
import com.example.kindspark.notifications.NotificationScheduler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class HomeUiState(
    val dailyPrompt: KindnessPromptWithCompletion? = null,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val totalCompleted: Int = 0,
    val isCompleted: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null,
    val showCelebration: Boolean = false,
    val celebrationMilestone: Int? = null,
    val userProgress: UserProgress? = null
)

class HomeViewModel(
    private val repository: KindnessRepository,
    private val preferencesManager: UserPreferencesManager,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDailyPrompt()
        loadCurrentStreak()
    }

    private fun loadDailyPrompt() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val promptWithCompletion = repository.getDailyPrompt()

                _uiState.value = _uiState.value.copy(
                    dailyPrompt = promptWithCompletion,
                    isCompleted = promptWithCompletion.completion != null,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load daily prompt: ${e.message}"
                )
            }
        }
    }

    private fun loadCurrentStreak() {
        viewModelScope.launch {
            try {
                val userProgress = repository.getUserProgress()
                _uiState.value = _uiState.value.copy(
                    currentStreak = userProgress.currentStreak,
                    bestStreak = userProgress.bestStreak,
                    totalCompleted = userProgress.totalCompleted,
                    userProgress = userProgress
                )
            } catch (e: Exception) {
                // Handle error silently for streak, it's not critical
            }
        }
    }

    fun markAsCompleted(notes: String = "") {
        viewModelScope.launch {
            try {
                val currentPrompt = _uiState.value.dailyPrompt?.prompt
                if (currentPrompt != null) {
                    val today = Date()
                    val completionId = repository.markPromptAsCompleted(currentPrompt.id, today, notes)

                    // Update streak and get updated progress
                    val updatedProgress = repository.updateStreakOnCompletion(today)

                    // Check for milestone celebration
                    val milestones = repository.getStreakMilestones(updatedProgress.currentStreak)
                    val shouldCelebrate = milestones.isNotEmpty()

                    // Update UI state
                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val completion = KindnessCompletion(
                        id = completionId,
                        promptId = currentPrompt.id,
                        completedDate = dateFormatter.format(today),
                        notes = notes
                    )

                    _uiState.value = _uiState.value.copy(
                        dailyPrompt = _uiState.value.dailyPrompt?.copy(completion = completion),
                        isCompleted = true,
                        currentStreak = updatedProgress.currentStreak,
                        bestStreak = updatedProgress.bestStreak,
                        totalCompleted = updatedProgress.totalCompleted,
                        userProgress = updatedProgress,
                        showCelebration = shouldCelebrate,
                        celebrationMilestone = milestones.firstOrNull()
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to mark as completed: ${e.message}"
                )
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            try {
                val completion = _uiState.value.dailyPrompt?.completion
                if (completion != null) {
                    val newFavoriteStatus = !completion.isFavorite
                    repository.toggleFavorite(completion)

                    // Update UI state with the new favorite status
                    _uiState.value = _uiState.value.copy(
                        dailyPrompt = _uiState.value.dailyPrompt?.copy(
                            completion = completion.copy(isFavorite = newFavoriteStatus)
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to toggle favorite: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun refreshPrompt() {
        loadDailyPrompt()
        loadCurrentStreak()
    }

    fun skipCurrentPrompt() {
        viewModelScope.launch {
            try {
                val currentPrompt = _uiState.value.dailyPrompt?.prompt
                if (currentPrompt != null) {
                    // Skip the current prompt
                    repository.skipPrompt(currentPrompt.id, Date())

                    // Get the next available prompt
                    val nextPrompt = repository.getNextAvailablePrompt()

                    // Update UI state with the new prompt
                    _uiState.value = _uiState.value.copy(
                        dailyPrompt = nextPrompt,
                        isCompleted = false // New prompt is not completed
                    )

                    // Clean up old skipped prompts (keep only last 7 days)
                    repository.cleanupOldSkippedPrompts()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to skip prompt: ${e.message}"
                )
            }
        }
    }

    fun dismissCelebration() {
        _uiState.value = _uiState.value.copy(
            showCelebration = false,
            celebrationMilestone = null
        )
    }
}
