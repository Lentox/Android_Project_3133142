package com.example.android_project_3133142

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable function for displaying weather data.
@Composable
fun WeatherDisplay(weatherData: WeatherData) {
    // A vertical layout to display weather information.
    Column(
        modifier = Modifier.fillMaxSize(), // Fills the maximum size available for the column.
        horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally.
        verticalArrangement = Arrangement.Center // Centers children vertically.
    ) {
        // Icon representing the current weather.
        Icon(
            imageVector = weatherData.currentWeatherIcon, // Uses the weather icon from the data.
            contentDescription = "Aktuelles Wetter", // Accessibility description.
            modifier = Modifier.size(100.dp) // Sets the size of the icon.
        )
        // Text displaying the current temperature.
        Text(
            "${weatherData.currentTemperature}°",
            fontWeight = FontWeight.Bold, // Bold font weight for emphasis.
            fontSize = 30.sp, // Font size.
            fontFamily = FontFamily.SansSerif // Font family.
        )

        // Text indicating the possibility of precipitation.
        if(weatherData.isPrecipitation) {
            Text(
                "Niederschlag",
                fontSize = 19.sp, // Font size.
                fontFamily = FontFamily.SansSerif // Font family.
            )
        }

        // Row layout for displaying maximum and minimum temperature.
        Row {
            // Text for maximum temperature.
            Text(
                "Max: ${weatherData.maxTemperature}°",
                fontSize = 19.sp, // Font size.
                fontFamily = FontFamily.SansSerif // Font family.
            )
            Spacer(modifier = Modifier.width(16.dp)) // Horizontal spacer.
            // Text for minimum temperature.
            Text(
                "Min: ${weatherData.minTemperature}°",
                fontSize = 19.sp, // Font size.
                fontFamily = FontFamily.SansSerif // Font family.
            )
        }

        Spacer(modifier = Modifier.height(50.dp)) // Vertical spacer.

        // Displaying a transparent box with hourly forecasts.
        TransparentBoxWithHourlyForecast(weatherData.hourlyForecast, "July, 23")
    }
}

// Composable function for a transparent box that displays hourly weather forecasts.
@Composable
fun TransparentBoxWithHourlyForecast(hourlyForecast: List<HourlyForecast>, currentDate: String) {
    // A box with rounded corners and semi-transparent background.
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp)) // Rounded corners for the box.
            .background(Color(0xFFE7E0EC).copy(alpha = 0.8f)) // Semi-transparent background color.
            .padding(16.dp) // Padding around the box.
            .wrapContentSize() // Adjusts the size of the box to its content.
    ) {
        // Column layout for arranging elements vertically inside the box.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally // Centers children horizontally.
        ) {
            // Row layout for "Today" label and current date.
            Row(
                modifier = Modifier.wrapContentSize(), // Adjusts the size to its content.
                horizontalArrangement = Arrangement.SpaceBetween // Space between elements.
            ) {
                Text("Today", style = MaterialTheme.typography.bodyMedium) // "Today" label.
                Spacer(modifier = Modifier.width(110.dp)) // Horizontal spacer.
                Text(currentDate, style = MaterialTheme.typography.bodyMedium) // Current date.
            }

            Spacer(modifier = Modifier.height(8.dp)) // Vertical spacer.

            // Row layout for displaying hourly weather forecasts.
            Row(
                modifier = Modifier
                    .wrapContentSize(), // Adjusts the size to its content.
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between elements.
            ) {
                // Iterating over hourly forecasts to display each.
                hourlyForecast.forEach { forecast ->
                    HourlyWeatherDisplay(forecast)
                }
            }
        }
    }
}

// Composable function to display hourly weather data.
@Composable
fun HourlyWeatherDisplay(forecast: HourlyForecast) {
    // Column layout for arranging icon and texts vertically.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally // Centers children horizontally.
    ) {
        // Text displaying the temperature.
        Text(
            text = "${forecast.temperature}°",
            style = MaterialTheme.typography.bodyMedium // Text style.
        )
        Spacer(modifier = Modifier.height(5.dp)) // Vertical spacer.
        // Icon representing the weather.
        Icon(
            imageVector = forecast.weatherIcon, // Weather icon.
            contentDescription = "Wettericon" // Accessibility description.
        )
        Spacer(modifier = Modifier.height(5.dp)) // Vertical spacer.
        // Text displaying the time.
        Text(
            text = forecast.time,
            style = MaterialTheme.typography.bodyMedium // Text style.
        )
    }
}

// Data class representing overall weather data.
data class WeatherData(
    val currentWeatherIcon: ImageVector, // Icon for the current weather.
    val currentTemperature: Int, // Current temperature.
    val maxTemperature: Int, // Maximum temperature.
    val minTemperature: Int, // Minimum temperature.
    val isPrecipitation: Boolean, // Flag for precipitation.
    val hourlyForecast: List<HourlyForecast> // List of hourly forecasts.
)

// Data class representing hourly weather forecast.
data class HourlyForecast(
    val temperature: Int, // Temperature for the hour.
    val weatherIcon: ImageVector, // Icon for the weather.
    val time: String // Time of the forecast.
)

// Function to get sample weather data (used for demonstration purposes).
fun getSampleWeatherData(): WeatherData {
    return WeatherData(
        currentWeatherIcon = Icons.Default.WbSunny, // Sample sunny icon.
        currentTemperature = 23, // Sample current temperature.
        maxTemperature = 28, // Sample maximum temperature.
        minTemperature = 18, // Sample minimum temperature.
        isPrecipitation = false, // Sample precipitation flag.
        hourlyForecast = listOf(
            // List of sample hourly forecasts.
            HourlyForecast(22, Icons.Default.WbSunny, "12:00"),
            HourlyForecast(24, Icons.Default.WbSunny, "13:00"),
            HourlyForecast(25, Icons.Default.WbSunny, "14:00"),
            HourlyForecast(23, Icons.Default.WbSunny, "15:00")
        )
    )
}

