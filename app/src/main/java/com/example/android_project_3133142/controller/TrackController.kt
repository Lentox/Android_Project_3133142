package com.example.android_project_3133142.controller

import com.example.android_project_3133142.objects.Slope
import com.example.android_project_3133142.currentDisplayedCard
import com.example.android_project_3133142.isCardDisplayed
import kotlin.math.atan

fun displaySlopeCard(slope: Slope){
    isCardDisplayed = true
    currentDisplayedCard = slope
}

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