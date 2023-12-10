package com.example.android_project_3133142.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.android_project_3133142.R

// NavigationBottomBar of the App.
@Composable
fun BottomBar(
    navController: NavHostController, // Navigation controller for managing navigation events.
) {
    // List of destinations to be displayed in the BottomBar.
    val screens = listOf(
        Destinations.Home,
        Destinations.Track,
        Destinations.Slopes,
        Destinations.Profile
    )

    // The actual BottomBar layout component.
    NavigationBar(
        // Modifier for the NavigationBar's appearance and dimensions.
        modifier = Modifier
            .padding(10.dp) // Adds padding around the NavigationBar.
            .clip(RoundedCornerShape(25)), // Rounds the corners of the NavigationBar.
        containerColor = colorResource(id = R.color.navBackground), // Sets the background color of the NavigationBar.

    ) {
        // Retrieves the current back stack entry and the current route.
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // Iterates through each screen and creates a tab for it.
        screens.forEach { screen ->
            NavigationBarItem(
                // Label for the navigation item, showing the title of the screen.
                label = {
                    Text(text = screen.title!!)
                },
                // Icon for the navigation item.
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                // Determines if the navigation item is currently selected.
                selected = currentRoute == screen.route,
                // Action on click: navigates to the respective screen.
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                // Customizes the colors for the navigation items.
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, // Color for unselected item text.
                    selectedTextColor = Color.Black // Color for selected item text.
                ),
            )
        }
    }
}