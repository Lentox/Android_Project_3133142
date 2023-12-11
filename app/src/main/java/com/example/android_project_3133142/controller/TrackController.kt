package com.example.android_project_3133142.controller

import kotlin.math.atan

/**
 * Calculates the slope percentage and slope angle based on the given height difference
 * and horizontal distance.
 *
 * @param heightDifference The difference in height between two points.
 * @param horizontalDistance The horizontal distance between two points.
 * @return A Pair containing the slope percentage and slope angle.
 * @throws IllegalArgumentException if the horizontal distance is 0.
 */
fun calculateGradient(heightDifference: Double, horizontalDistance: Double): Pair<Double, Double> {
    // Check for division by zero and throw an exception if horizontal distance is 0.
    if (horizontalDistance == 0.0) {
        throw IllegalArgumentException("Horizontal distance cannot be 0.")
    }

    // Conversion kilometers to meters
    val meterDistance = horizontalDistance * 1000

    // Calculate slope percentage and slope angle.
    val slopePercentage = (heightDifference / meterDistance) * 100
    val slopeAngle = Math.toDegrees(atan(heightDifference / meterDistance))

    // Return the calculated values as a Pair.
    return Pair(slopePercentage, slopeAngle)
}

/**
 * Formats elapsed time in milliseconds to a string representation in the format "HH:mm:ss".
 *
 * @param elapsedTime The elapsed time in milliseconds.
 * @return A formatted string representing the elapsed time.
 */
fun formatElapsedTime(elapsedTime: Long): String {
    // Calculate hours, minutes, and seconds from the elapsed time.
    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = elapsedTime / (1000 * 60 * 60)

    // Format the time components into "HH:mm:ss" and return the result.
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

/**
 * Sealed class representing different unit types (Speed or Vertical).
 */
sealed class UnitType

/**
 * Data class representing a Speed unit.
 *
 * @param unit The Speed unit (e.g., KMH, MPH).
 */
data class SpeedUnit(val unit: Speed) : UnitType()

/**
 * Data class representing a Vertical unit.
 *
 * @param unit The Vertical unit (e.g., M, FT).
 */
data class VerticalUnit(val unit: Vertical) : UnitType()

/**
 * Enum class representing different speed units.
 *
 * @param label The label associated with the speed unit.
 */
enum class Speed(val label: String) {
    KMH("km/h"),
    MPH("mph")
}

/**
 * Enum class representing different vertical units.
 *
 * @param label The label associated with the vertical unit.
 */
enum class Vertical(val label: String) {
    M("m"),
    FT("ft")
}
