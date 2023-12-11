package com.example.android_project_3133142.controller

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Speed
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.android_project_3133142.slopesArray

var statistics = mutableListOf<Statistic>()

// Data class representing a statistic, consisting of a label, a value, and an icon.
data class Statistic(
    val label: String,
    val value: String,
    val icon: ImageVector
)
fun reCalcStatistic(){

    var overallMaxSpeed = 0
    var overallDistance = 0
    var overallMaxHeight = 0

    for (slope in slopesArray){

        if (overallMaxHeight < slope.altitude.max)
            overallMaxHeight = slope.altitude.max

        overallDistance += slope.distance

        if (overallMaxSpeed < slope.maxVelocity)
            overallMaxSpeed = slope.maxVelocity

    }

    statistics = mutableListOf(
        Statistic("Overall Max Speed", "$overallMaxSpeed km/h", Icons.Default.Speed),
        Statistic("Overall Distance", "$overallDistance km", Icons.Default.DownhillSkiing),
        Statistic("Overall Max Height", "$overallMaxHeight m", Icons.Default.Height)
    )
}