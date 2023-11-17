package com.example.android_project_3133142

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Place
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

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        WeatherDisplay(weatherData = getSampleWeatherData())
    }
}

@Composable
fun TrackScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Text(
            text = "Track",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp),

            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif

        )
        GridLayout()
    }
}

@Composable
fun SlopesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Text(
            text = "Slopes",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif
        )
        val exampleData = listOf(
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
        ScrollableCardList(exampleData) { index ->
            // Hier Aktion für Klick auf das Icon, z.B. Toast-Nachricht anzeigen
            println("Icon bei Index $index geklickt")
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Profile Screen",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}