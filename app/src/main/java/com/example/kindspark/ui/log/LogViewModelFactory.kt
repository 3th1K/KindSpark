package com.example.kindspark.ui.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kindspark.data.KindnessRepository

class LogViewModelFactory(
    private val repository: KindnessRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
