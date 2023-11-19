package com.example.android_project_3133142

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Height
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
            fontFamily = FontFamily.SansSerif
        )
        // Calls the GridLayout function to display a grid layout.
        GridLayout()
    }
}

// Composable function for the Slopes Screen.
@Composable
fun SlopesScreen() {
    // Column layout for the slopes screen.
    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the maximum size available.
            .paint(
                painterResource(id = R.drawable.background), // Sets the background image.
                contentScale = ContentScale.FillBounds // Scales the background image to fill the bounds.
            )
            .wrapContentSize(Alignment.TopCenter) // Wraps the content size and aligns it at the top center.
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
            fontFamily = FontFamily.SansSerif
        )



        // Example data for the slopes screen cards.
        val exampleData = listOf(
            // Creating instances of CardData for the cards.
            CardData(
                imageRes = R.drawable.stchrisophtmp,
                location = "St.Christoph",
                details = listOf(
                    IconTextPair(Icons.Default.DownhillSkiing, "21,2 km"),
                    IconTextPair(Icons.Default.Landscape, "3.565 m"),
                    IconTextPair(Icons.Default.Speed, "82,5 km/h")
                ),
                date = "Oktober 30, 2023"
            ),
            CardData(
                imageRes = R.drawable.stchrisophtmp,
                location = "St.Christoph",
                details = listOf(
                    IconTextPair(Icons.Default.DownhillSkiing, "21,2 km"),
                    IconTextPair(Icons.Default.Landscape, "3.565 m"),
                    IconTextPair(Icons.Default.Speed, "82,5 km/h")
                ),
                date = "Oktober 30, 2023"
            ),
            CardData(
                imageRes = R.drawable.stchrisophtmp,
                location = "St.Christoph",
                details = listOf(
                    IconTextPair(Icons.Default.DownhillSkiing, "21,2 km"),
                    IconTextPair(Icons.Default.Landscape, "3.565 m"),
                    IconTextPair(Icons.Default.Speed, "82,5 km/h")
                ),
                date = "Oktober 30, 2023"
            ),
            CardData(
                imageRes = R.drawable.stchrisophtmp,
                location = "St.Christoph",
                details = listOf(
                    IconTextPair(Icons.Default.DownhillSkiing, "21,2 km"),
                    IconTextPair(Icons.Default.Landscape, "3.565 m"),
                    IconTextPair(Icons.Default.Speed, "82,5 km/h")
                ),
                date = "Oktober 30, 2023"
            ),

        )
        // Displaying a scrollable list of cards with the example data.
        ScrollableCardList(exampleData) { index ->
            // Placeholder for icon click handling. Currently, it prints the index of the clicked icon.
            println("Icon bei Index $index geklickt")
        }
    }
}

// Composable function for the Profile Screen.
@Composable
fun ProfileScreen() {
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
            fontFamily = FontFamily.SansSerif
        )
        // Statistics data for the profile screen.
        val statistics = listOf(
            Statistic("Overall Max Speed", "120 km/h", Icons.Default.Speed),
            Statistic("Overall Distance", "1265 km", Icons.Default.DownhillSkiing),
            Statistic("Overall Max Height", "3565 m", Icons.Default.Height)
        )
        // Displaying a view with profile statistics.
        ProfileStatisticsView(statistics)
    }
}
