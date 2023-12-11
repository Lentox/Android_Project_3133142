package com.example.android_project_3133142.controller

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Speed
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.android_project_3133142.slopesArray

/**
 * List to store statistics, each represented by a Statistic data class.
 */
var statistics = mutableListOf<Statistic>()

/**
 * Data class representing a statistic, consisting of a label, a value, and an icon.
 *
 * @param label The label describing the statistic.
 * @param value The value of the statistic.
 * @param icon The icon associated with the statistic.
 */
data class Statistic(
    val label: String,
    val value: String,
    val icon: ImageVector
)

/**
 * Recalculates and updates the statistics based on the data in slopesArray.
 */
fun reCalcStatistic() {
    // Initialize variables to hold overall statistics.
    var overallMaxSpeed = 0
    var overallDistance = 0
    var overallMaxHeight = 0

    // Iterate through slopesArray to calculate overall statistics.
    for (slope in slopesArray) {
        // Update overall max height if the current slope's max altitude is greater.
        if (overallMaxHeight < slope.altitude.max)
            overallMaxHeight = slope.altitude.max

        // Accumulate overall distance.
        overallDistance += slope.distance

        // Update overall max speed if the current slope's max velocity is greater.
        if (overallMaxSpeed < slope.maxVelocity)
            overallMaxSpeed = slope.maxVelocity
    }

    // Update the statistics list with the recalculated values.
    statistics = mutableListOf(
        Statistic("Overall Max Speed", "$overallMaxSpeed km/h", Icons.Default.Speed),
        Statistic("Overall Distance", "$overallDistance km", Icons.Default.DownhillSkiing),
        Statistic("Overall Max Height", "$overallMaxHeight m", Icons.Default.Height)
    )
}
