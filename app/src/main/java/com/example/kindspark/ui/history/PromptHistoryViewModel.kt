package com.example.kindspark.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kindspark.data.KindnessCompletion
import com.example.kindspark.data.KindnessPrompt
import com.example.kindspark.data.KindnessRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PromptHistoryItem(
    val completion: KindnessCompletion,
    val prompt: KindnessPrompt
)

enum class HistoryFilter {
    ALL, COMPLETED, FAVORITES
}

data class PromptHistoryUiState(
    val historyItems: List<PromptHistoryItem> = emptyList(),
    val currentFilter: HistoryFilter = HistoryFilter.ALL,
    val isLoading: Boolean = false,
    val error: String? = null
)

class PromptHistoryViewModel(
    private val repository: KindnessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PromptHistoryUiState())
    val uiState: StateFlow<PromptHistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Get all completions from repository
                repository.getAllCompletions().collect { completions ->
                    val historyItems = completions.mapNotNull { completion ->
                        val prompt = repository.getPromptById(completion.promptId)
                        prompt?.let { PromptHistoryItem(completion, it) }
                    }

                    _uiState.value = _uiState.value.copy(
                        historyItems = filterItems(historyItems, _uiState.value.currentFilter),
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun setFilter(filter: HistoryFilter) {
        viewModelScope.launch {
            val currentItems = _uiState.value.historyItems
            val allItems = if (_uiState.value.currentFilter == HistoryFilter.ALL) {
                currentItems
            } else {
                // Re-fetch all items if we're currently filtered
                try {
                    val completions = repository.getAllCompletions().first()
                    completions.mapNotNull { completion ->
                        val prompt = repository.getPromptById(completion.promptId)
                        prompt?.let { PromptHistoryItem(completion, it) }
                    }
                } catch (e: Exception) {
                    currentItems
                }
            }

            _uiState.value = _uiState.value.copy(
                currentFilter = filter,
                historyItems = filterItems(allItems, filter)
            )
        }
    }

    private fun filterItems(items: List<PromptHistoryItem>, filter: HistoryFilter): List<PromptHistoryItem> {
        return when (filter) {
            HistoryFilter.ALL -> items
            HistoryFilter.COMPLETED -> items // All items are already completed
            HistoryFilter.FAVORITES -> items.filter { it.completion.isFavorite }
        }
    }

    fun toggleFavorite(completion: KindnessCompletion) {
        viewModelScope.launch {
            try {
                val updatedCompletion = completion.copy(isFavorite = !completion.isFavorite)
                repository.updateCompletion(updatedCompletion)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun deleteCompletion(completion: KindnessCompletion) {
        viewModelScope.launch {
            try {
                repository.deleteCompletion(completion)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
}
