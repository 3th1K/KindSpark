package com.example.kindspark.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

object DateUtils {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val displayFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    fun formatDateForDisplay(date: LocalDate): String {
        return date.format(displayFormatter)
    }

    fun formatDateForDatabase(date: LocalDate): String {
        return date.format(dateFormatter)
    }

    fun parseDateFromDatabase(dateString: String): LocalDate {
        return LocalDate.parse(dateString, dateFormatter)
    }

    fun getDayOfWeekName(date: LocalDate): String {
        return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    fun isToday(date: LocalDate): Boolean {
        return date == LocalDate.now()
    }

    fun isYesterday(date: LocalDate): Boolean {
        return date == LocalDate.now().minusDays(1)
    }

    fun getRelativeDateString(date: LocalDate): String {
        return when {
            isToday(date) -> "Today"
            isYesterday(date) -> "Yesterday"
            else -> formatDateForDisplay(date)
        }
    }
}
