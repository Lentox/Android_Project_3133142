package com.example.android_project_3133142.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

// Sealed class to represent, navigation destinations in the app.
sealed class Destinations(
    val route: String, // The navigation route for the destination.
    val title: String? = null, // Optional title for the destination.
    val icon: ImageVector? = null // Optional icon for the destination.
) {
    // Object representing the Home destination.
    object Home : Destinations(
        route = "home_screen",
        title = "Home",
        icon = Icons.Filled.Home
    )
    // Object representing the Track destination.
    object Track : Destinations(
        route = "track_screen",
        title = "Track",
        icon = Icons.Filled.DownhillSkiing
    )
    // Object representing the Slopes destination.
    object Slopes : Destinations(
        route = "slopes_screen",
        title = "Slopes",
        icon = Icons.Filled.Landscape
    )
    // Object representing the Profile destination.
    object Profile : Destinations(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Filled.Person
    )
}