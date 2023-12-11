package com.example.android_project_3133142.controller

import kotlin.math.atan

fun calculateGradient(heightDifference: Double, horizontalDistance: Double): Pair<Double, Double> {
    if (horizontalDistance == 0.0) {
        throw IllegalArgumentException("Horizontal distance cannot be 0.")
    }
    val slopePercentage = (heightDifference / horizontalDistance) * 100
    val slopeAngle = Math.toDegrees(atan(heightDifference / horizontalDistance))
    return Pair(slopePercentage, slopeAngle)
}

fun formatElapsedTime(elapsedTime: Long): String {
    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = elapsedTime / (1000 * 60 * 60)

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

sealed class UnitType
data class SpeedUnit(val unit: Speed) : UnitType()
data class VerticalUnit(val unit: Vertical) : UnitType()

enum class Speed(val label: String) {
    KMH("km/h"),
    MPH("mph")
}

enum class Vertical(val label: String) {
    M("m"),
    FT("ft")
}