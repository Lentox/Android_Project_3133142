package com.example.android_project_3133142.manager

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import java.time.LocalTime

var weatherDataAPI: WeatherService.WeatherData? by mutableStateOf(null)
var forecastOutputs: MutableList<WeatherService.HourlyForecast> = mutableListOf()

class WeatherService {

    private val apiKey = "d7e6a10442444c3fbf6113924231511"

    data class WeatherData(
        val location: String,
        val temp: Double,
        val maxTemp: Double,
        val minTemp: Double,
        val conditionIconUrl: String,
        val humidity: Int,
        val cloud: Int,
        val conditionText: String,
        val hourlyForecasts: MutableList<HourlyForecast>
    )

    data class HourlyForecast(
        val hour: Int,
        val temp: Double,
        val conditionIcon: String
    )

    @SuppressLint("SuspiciousIndentation")
    fun getWeather(latitude: Double, longitude: Double) {

        val url = "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$latitude,$longitude&aqi=no"

        forecastOutputs.clear()
        weatherDataAPI?.hourlyForecasts?.clear()

        Fuel.get(url).responseJson { _, _, result ->
            result.fold(success = { json ->
                val jsonResponse = json.obj()
                val location = jsonResponse.getJSONObject("location").getString("name")
                val current = jsonResponse.getJSONObject("current")
                val temp = current.getDouble("temp_c")
                val conditionText = current.getJSONObject("condition").getString("text")
                val conditionIconUrl = current.getJSONObject("condition").getString("icon")
                val humidity = current.getInt("humidity")
                val cloud = current.getInt("cloud")

                // Reading out the daily data for the minimum and maximum temperature
                val forecast = jsonResponse.getJSONObject("forecast")
                val forecastDay = forecast.getJSONArray("forecastday")
                val todayForecast = forecastDay.getJSONObject(0)
                val dayData = todayForecast.getJSONObject("day")
                val minTemp = dayData.getDouble("mintemp_c")
                val maxTemp = dayData.getDouble("maxtemp_c")

                // Reading out the forecast data for the next 24 hours
                val hourlyForecast = todayForecast.getJSONArray("hour")

                for (i in 0 until hourlyForecast.length()) {
                    val hourForecast = hourlyForecast.getJSONObject(i)
                    val forecastTime = hourForecast.getString("time").replace(" ", "T").split("T")[1]

                    val hourlyTemp = hourForecast.getDouble("temp_c")
                    val hourlyConditionIcon = hourForecast.getJSONObject("condition").getString("icon")



                    fun getNextHour(hour: Int): Int {
                        return (hour + 1) % 24 // Modulo 24 provides the wrap-around for 24
                    }

                    val now = LocalTime.now().hour

                    val hoursToCheck = listOf(now, getNextHour(now), getNextHour(getNextHour(now)), getNextHour(getNextHour(getNextHour(now))))

                    val updatedHours = hoursToCheck.map {
                        if (it < 10) "0$it" else "$it"
                    }

                    val forecastTimeHour = forecastTime.substring(0,2)

                    for (z in hoursToCheck.indices){
                        for (j in forecastTime.indices){
                            if (updatedHours[z] == forecastTimeHour){
                                val forecastWeatherData = HourlyForecast(
                                    hour = forecastTimeHour.toInt(),
                                    temp = hourlyTemp,
                                    conditionIcon = hourlyConditionIcon
                                )
                                forecastOutputs.add(forecastWeatherData)
                                break
                            }
                        }
                    }
                }
                // Create and use weather data object
                weatherDataAPI = WeatherData(location, temp, maxTemp, minTemp, conditionIconUrl, humidity, cloud, conditionText, forecastOutputs)
                println(forecastOutputs)

            }, failure = { error ->
                println("An error occurred: ${error.message}")
            })
        }
    }
}