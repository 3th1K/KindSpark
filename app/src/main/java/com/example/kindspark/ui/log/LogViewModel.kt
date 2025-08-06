package com.example.kindspark.ui.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kindspark.data.KindnessCompletion
import com.example.kindspark.data.KindnessPrompt
import com.example.kindspark.data.KindnessRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class LogUiState(
    val completions: List<CompletionWithPrompt> = emptyList(),
    val favorites: List<CompletionWithPrompt> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

data class CompletionWithPrompt(
    val completion: KindnessCompletion,
    val prompt: KindnessPrompt
)

class LogViewModel(
    private val repository: KindnessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogUiState())
    val uiState: StateFlow<LogUiState> = _uiState.asStateFlow()

    init {
        loadCompletions()
    }

    private fun loadCompletions() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                repository.getAllCompletions().collect { completions ->
                    val completionsWithPrompts = completions.mapNotNull { completion ->
                        val prompt = repository.getPromptById(completion.promptId)
                        prompt?.let { CompletionWithPrompt(completion, it) }
                    }

                    val favorites = completionsWithPrompts.filter { it.completion.isFavorite }

                    _uiState.value = _uiState.value.copy(
                        completions = completionsWithPrompts,
                        favorites = favorites,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load completions: ${e.message}"
                )
            }
        }
    }

    fun toggleFavorite(completion: KindnessCompletion) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(completion)
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
}
