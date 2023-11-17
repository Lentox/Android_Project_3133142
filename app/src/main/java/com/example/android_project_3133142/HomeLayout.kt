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

@Composable
fun WeatherDisplay(weatherData: WeatherData) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = weatherData.currentWeatherIcon,
            contentDescription = "Aktuelles Wetter",
            modifier = Modifier.size(100.dp)
        )
        Text(
            "${weatherData.currentTemperature}°",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif
        )


        Text(
            "Niederschlag",
            fontSize = 19.sp,
            fontFamily = FontFamily.SansSerif
        )

        Row {
            Text(
                "Max: ${weatherData.maxTemperature}°",
                fontSize = 19.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Min: ${weatherData.minTemperature}°",
                fontSize = 19.sp,
                fontFamily = FontFamily.SansSerif
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        TransparentBoxWithHourlyForecast(weatherData.hourlyForecast, "July, 23")
    }
}

@Composable
fun TransparentBoxWithHourlyForecast(hourlyForecast: List<HourlyForecast>, currentDate: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.8f))
            .padding(16.dp)
            .wrapContentSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Today", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(110.dp))
                Text(currentDate, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                hourlyForecast.forEach { forecast ->
                    HourlyWeatherDisplay(forecast)
                }
            }
        }
    }
}



@Composable
fun HourlyWeatherDisplay(forecast: HourlyForecast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${forecast.temperature}°",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(5.dp))
        Icon(
            imageVector = forecast.weatherIcon,
            contentDescription = "Wettericon"
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = forecast.time,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


data class WeatherData(
    val currentWeatherIcon: ImageVector,
    val currentTemperature: Int,
    val maxTemperature: Int,
    val minTemperature: Int,
    val isPrecipitation: Boolean,
    val hourlyForecast: List<HourlyForecast>
)

data class HourlyForecast(
    val temperature: Int,
    val weatherIcon: ImageVector,
    val time: String
)

fun getSampleWeatherData(): WeatherData {
    return WeatherData(
        currentWeatherIcon = Icons.Default.WbSunny,
        currentTemperature = 23,
        maxTemperature = 28,
        minTemperature = 18,
        isPrecipitation = false,
        hourlyForecast = listOf(
            HourlyForecast(22, Icons.Default.WbSunny, "12:00"),
            HourlyForecast(24, Icons.Default.WbSunny, "13:00"),
            HourlyForecast(25, Icons.Default.WbSunny, "14:00"),
            HourlyForecast(23, Icons.Default.WbSunny, "15:00")
        )
    )
}
