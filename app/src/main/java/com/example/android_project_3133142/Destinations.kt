package com.example.android_project_3133142

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object Home : Destinations(
        route = "home_screen",
        title = "Home",
        icon = Icons.Filled.Home
    )

    object Track : Destinations(
        route = "track_screen",
        title = "Track",
        icon = Icons.Filled.DownhillSkiing
    )

    object Slopes : Destinations(
        route = "slopes_screen",
        title = "Slopes",
        icon = Icons.Filled.Landscape
    )

    object Profile : Destinations(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Filled.Person
    )

}