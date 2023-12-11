package com.example.android_project_3133142.controller

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Formats the month name with mixed case (e.g., January).
 *
 * @param date The LocalDate object representing a date.
 * @return A formatted month name with the first letter in uppercase and the rest in lowercase.
 */
fun formatMonthNameWithMixedCase(date: LocalDate): String {
    // Retrieve the month from the LocalDate object.
    val month = date.month

    // Get the full month name using the default locale.
    val monthName = month.getDisplayName(TextStyle.FULL, Locale.getDefault())

    // Format the month name with the first letter in uppercase and the rest in lowercase.
    return monthName.substring(0, 1).uppercase(Locale.ROOT) + monthName.substring(1)
        .lowercase(Locale.ROOT)
}