package com.example.android_project_3133142

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Destinations.Home.route) {
        composable(Destinations.Home.route) {
            HomeScreen()
        }
        composable(Destinations.Track.route) {
            TrackScreen()
        }
        composable(Destinations.Slopes.route) {
            SlopesScreen()
        }
        composable(Destinations.Profile.route) {
            ProfileScreen()
        }
    }
}