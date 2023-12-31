package com.example.android_project_3133142.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.android_project_3133142.controller.displaySlopeCard
import com.example.android_project_3133142.navigation.Destinations
import com.example.android_project_3133142.R
import com.example.android_project_3133142.Ubuntu
import com.example.android_project_3133142.controller.CardData
import com.example.android_project_3133142.controller.IconTextPair
import com.example.android_project_3133142.controller.statistics
import com.example.android_project_3133142.slopesArray

// Composable function for the Home Screen.
@Composable
fun HomeScreen() {
    // Column layout for the home screen.
    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the maximum size available.
            .paint(
                painterResource(id = R.drawable.background), // Sets the background image.
                contentScale = ContentScale.FillBounds // Scales the background image to fill the bounds.
            )
    ) {
        // Display weather data on the home screen.
        WeatherDisplay()
    }
}

// Composable function for the Track Screen.
@Composable
fun TrackScreen() {
    // Column layout for the track screen.
    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the maximum size available.
            .paint(
                painterResource(id = R.drawable.background), // Sets the background image.
                contentScale = ContentScale.FillBounds // Scales the background image to fill the bounds.
            )
    ) {
        // Displaying a text label for the track screen.
        Text(
            text = "Track",
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Center the text horizontally.
                .padding(top = 5.dp, bottom = 5.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = Ubuntu
        )
        // Calls the GridLayout function to display a grid layout.
        GridLayout()
    }
}

// Composable function for the Slopes Screen.
@Composable
fun SlopesScreen(navController: NavHostController) {
    // Column layout for the slopes screen.
    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the maximum size available.
            .paint(
                painterResource(id = R.drawable.background), // Sets the background image.
                contentScale = ContentScale.FillBounds // Scales the background image to fill the bounds.
            )

    ) {
        // Displaying a text label for the slopes screen.
        Text(
            text = "Slopes",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Center the text horizontally.
                .padding(top = 5.dp, bottom = 5.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = Ubuntu
        )

        val exampleData = mutableListOf<CardData>()

        for(slope in slopesArray){
            exampleData.add(
                CardData(
                    imageRes = R.drawable.stchrisophtmp,
                    location = slope.location,
                    details = listOf(
                        IconTextPair(Icons.Default.DownhillSkiing, slope.distance.toString() + " km"),
                        IconTextPair(Icons.Default.Landscape, slope.altitude.delta.toString() + " m"),
                        IconTextPair(Icons.Default.Speed, slope.maxVelocity.toString() + " km/h")
                    ),
                    date = slope.date
                )
            )
        }
        // Displaying a scrollable list of cards with the example data.
        ScrollableCardList(exampleData) { index ->
            navController.navigate(Destinations.Track.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            displaySlopeCard(slopesArray[index])
        }
    }
}

// Composable function for the Profile Screen.
@Composable
fun StatisticsScreen() {
    // Column layout for the profile screen.
    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the maximum size available.
            .paint(
                painterResource(id = R.drawable.background), // Sets the background image.
                contentScale = ContentScale.FillBounds // Scales the background image to fill the bounds.
            )
            .wrapContentSize(Alignment.Center) // Wraps the content size and centers it.
    ) {
        // Displaying a text label for the profile screen.
        Text(
            text = "Statistic",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally), // Center the text horizontally.
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = Ubuntu
        )

        // Displaying a view with profile statistics.
        ProfileStatisticsView(statistics)
    }
}
