package com.example.android_project_3133142

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

// Composable function for displaying weather data.
@Composable
fun WeatherDisplay() {

    // A vertical layout to display weather information.
    Column(
        modifier = Modifier.fillMaxSize(), // Fills the maximum size available for the column.
        horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally.
        verticalArrangement = Arrangement.Center // Centers children vertically.
    ) {
        // Icon representing the current weather.
        Image(
            painter = rememberImagePainter(data = ("https:" + weatherDataAPI?.conditionIconUrl)), // Uses the weather icon from the data.
            contentDescription = "Aktuelles Wetter", // Accessibility description.
            modifier = Modifier.size(100.dp) // Sets the size of the icon.
        )
        Text(
            "${weatherDataAPI?.location ?: "Loading..."}",
            fontWeight = FontWeight.Bold, // Bold font weight for emphasis.
            fontSize = 30.sp, // Font size.
            fontFamily = FontFamily.SansSerif // Font family.
        )
        // Text displaying the current temperature.
        Text(
            "${weatherDataAPI?.temp ?: "Loading..."}°",
            fontWeight = FontWeight.Bold, // Bold font weight for emphasis.
            fontSize = 30.sp, // Font size.
            fontFamily = FontFamily.SansSerif // Font family.
        )


        Text(
            "${weatherDataAPI?.conditionText ?: "Loading..."}",
            fontSize = 19.sp, // Font size.
            fontFamily = FontFamily.SansSerif, // Font family.
            fontWeight = FontWeight.SemiBold
        )

        // Row layout for displaying maximum and minimum temperature.
        Row {
            // Text for maximum temperature.
            Text(
                "Max: ${weatherDataAPI?.maxTemp ?: "Loading..."}°",
                fontSize = 19.sp, // Font size.
                fontFamily = FontFamily.SansSerif, // Font family.
                fontWeight = FontWeight.SemiBold // Font family.
            )
            Spacer(modifier = Modifier.width(16.dp)) // Horizontal spacer.
            // Text for minimum temperature.
            Text(
                "Min: ${weatherDataAPI?.minTemp ?: "Loading..."}°",
                fontSize = 19.sp, // Font size.
                fontFamily = FontFamily.SansSerif, // Font family.
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(50.dp)) // Vertical spacer.

        val currentDate = LocalDate.now()
        val formattedMonth = formatMonthNameWithMixedCase(currentDate)
        // Displaying a transparent box with hourly forecasts.
        TransparentBoxWithHourlyForecast(weatherDataAPI?.hourlyForecasts, "" + formattedMonth + ", " + currentDate.dayOfMonth)
    }
}

fun formatMonthNameWithMixedCase(date: LocalDate): String {
    val month = date.month
    val monthName = month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase()
}

// Composable function for a transparent box that displays hourly weather forecasts.
@Composable
fun TransparentBoxWithHourlyForecast(hourlyForecast: MutableList<WeatherService.HourlyForecast>?, currentDate: String) {
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
                Text(
                    "Today",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold // Font family.
                    ) // "Today" label.
                Spacer(modifier = Modifier.width(110.dp)) // Horizontal spacer.
                Text(
                    currentDate,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                    )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Vertical spacer.

            // Row layout for displaying hourly weather forecasts.
            Row(
                modifier = Modifier
                    .wrapContentSize(), // Adjusts the size to its content.
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between elements.
            ) {
                // Iterating over hourly forecasts to display each.
                hourlyForecast?.forEach { forecast ->
                    HourlyWeatherDisplay(forecast)
                }
            }
        }
    }
}

// Composable function to display hourly weather data.
@Composable
fun HourlyWeatherDisplay(forecast: WeatherService.HourlyForecast) {
    // Column layout for arranging icon and texts vertically.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally // Centers children horizontally.
    ) {
        Text(

            text = "${forecast.temp}°",
            style = MaterialTheme.typography.bodyMedium, // Text style.
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold // Font family.
        )
        Spacer(modifier = Modifier.height(5.dp)) // Vertical spacer.
        // Icon representing the weather.
        Image(
            painter = rememberImagePainter(data = ("https:" + forecast.conditionIcon)), // Uses the weather icon from the data.
            contentDescription = "Wettericon", // Accessibility description.
            modifier = Modifier.size(35.dp) // Sets the size of the icon.
        )

        Spacer(modifier = Modifier.height(5.dp)) // Vertical spacer.
        // Text displaying the time.
        if (forecast.hour == LocalTime.now().hour){
            Text(
                text = "Now",
                style = MaterialTheme.typography.bodyMedium, // Text style.
                //fontSize = 19.sp, // Font size.
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold // Font family.
            )
        }else if(forecast.hour < 10){
            Text(
                text = "0" + forecast.hour.toString() + ":00",
                style = MaterialTheme.typography.bodyMedium, // Text style.
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold // Font family.
            )
        }else{
            Text(
                text = forecast.hour.toString() + ":00",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold // Font family.
            )
        }
    }
}


