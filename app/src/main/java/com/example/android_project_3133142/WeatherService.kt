package com.example.android_project_3133142

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import java.time.LocalDateTime
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

    fun getWeather(latitude: Double, longitude: Double) {

        val url = "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$latitude,$longitude&aqi=no"

        forecastOutputs.clear()
        weatherDataAPI?.hourlyForecasts?.clear()

        Fuel.get(url).responseJson { _, response, result ->
            result.fold(success = { json ->
                val jsonResponse = json.obj()
                val location = jsonResponse.getJSONObject("location").getString("name")
                val current = jsonResponse.getJSONObject("current")
                val temp = current.getDouble("temp_c")
                val conditionText = current.getJSONObject("condition").getString("text")
                val conditionIconUrl = current.getJSONObject("condition").getString("icon")
                val humidity = current.getInt("humidity")
                val cloud = current.getInt("cloud")

                // Auslesen der Tagesdaten für die Mindest- und Höchsttemperatur
                val forecast = jsonResponse.getJSONObject("forecast")
                val forecastdayArray = forecast.getJSONArray("forecastday")
                val todayForecast = forecastdayArray.getJSONObject(0)
                val dayData = todayForecast.getJSONObject("day")
                val minTemp = dayData.getDouble("mintemp_c")
                val maxTemp = dayData.getDouble("maxtemp_c")

                // Auslesen der Vorhersagedaten für die nächsten 24 Stunden
                val hourlyForecast = todayForecast.getJSONArray("hour")

                for (i in 0 until hourlyForecast.length()) {
                    val hourForecast = hourlyForecast.getJSONObject(i)
                    val forecastTime = LocalDateTime.parse(hourForecast.getString("time").replace(" ", "T"))

                    val hourlyTemp = hourForecast.getDouble("temp_c")
                    val hourlyConditionIcon = hourForecast.getJSONObject("condition").getString("icon")


                        if (forecastTime.hour == LocalTime.now().hour || forecastTime.hour == LocalTime.now().hour + 1 || forecastTime.hour == LocalTime.now().hour + 2 || forecastTime.hour == LocalTime.now().hour + 3) {
                            val forecastWeatherData = HourlyForecast(
                                hour = forecastTime.hour,
                                temp = hourlyTemp,
                                conditionIcon = hourlyConditionIcon
                            )

                            println("Forecast: " + forecastWeatherData)
                            forecastOutputs.add(forecastWeatherData)
                        }

                }
                // Wetterdatenobjekt erstellen und verwenden
                weatherDataAPI = WeatherData(location, temp, maxTemp, minTemp, conditionIconUrl, humidity, cloud, conditionText,
                    forecastOutputs)
            }, failure = { error ->
                println("An error occurred: ${error.message}")
            })
        }
    }
}
