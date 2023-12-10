package com.example.android_project_3133142.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_project_3133142.layout.HomeScreen
import com.example.android_project_3133142.layout.ProfileScreen
import com.example.android_project_3133142.layout.SlopesScreen
import com.example.android_project_3133142.layout.TrackScreen

// Composable function to define the navigation graph for the application.
@Composable
fun NavigationGraph(navController: NavHostController) {
    // NavHost is a composable that hosts a navigation graph for use with Jetpack Compose.
    // It controls the navigation within the app.
    NavHost(
        navController = navController, // The NavController that this NavHost interacts with.
        startDestination = Destinations.Home.route // The route for the start destination in the navigation graph.
    ) {
        // Defines a composable destination for the Home screen.
        composable(Destinations.Home.route) {
            HomeScreen() // Calls the HomeScreen composable function.
        }
        // Defines a composable destination for the Track screen.
        composable(Destinations.Track.route) {
            TrackScreen() // Calls the TrackScreen composable function.
        }
        // Defines a composable destination for the Slopes screen.
        composable(Destinations.Slopes.route) {
            SlopesScreen(navController) // Calls the SlopesScreen composable function.
        }
        // Defines a composable destination for the Profile screen.
        composable(Destinations.Profile.route) {
            ProfileScreen() // Calls the ProfileScreen composable function.
        }
    }
}
