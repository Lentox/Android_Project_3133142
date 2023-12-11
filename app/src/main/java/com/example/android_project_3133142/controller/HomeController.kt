package com.example.android_project_3133142.controller

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun formatMonthNameWithMixedCase(date: LocalDate): String {
    val month = date.month
    val monthName = month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return monthName.substring(0, 1).uppercase(Locale.ROOT) + monthName.substring(1)
        .lowercase(Locale.ROOT)
}