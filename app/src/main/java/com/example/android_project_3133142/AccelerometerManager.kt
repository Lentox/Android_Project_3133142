package com.example.android_project_3133142

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import kotlin.math.pow
import kotlin.math.sqrt

var totalDistance = 0f
var startTimeT = 0L

class AccelerometerManager(context: Context) : SensorEventListener {
    // Initialize the SensorManager to interact with the device's sensors.
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Get the linear acceleration sensor from the sensor manager.
    private var accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

    // Variables for managing the update intervals and storing sensor data.
    private var lastUpdateTime = 0L
    private var speed = 0f

    private var lastUpdateT = 0L
    private val updateInterval = 1000L // Update interval of 1 second (1000 milliseconds)

    private var filteredAcceleration = FloatArray(3) { 0f }
    private val alpha = 0.3f // Filterkonstante, zwischen 0 und 1, wobei ein höherer Wert eine stärkere Glättung bedeutet

    init {
        // Register the sensor event listener.
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    // Method to handle changes in sensor accuracy, can be implemented if needed.
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Implement reactions to changes in sensor accuracy if needed.
    }

    // Method to handle sensor data updates.
    override fun onSensorChanged(event: SensorEvent) {

        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastUpdateTime) > updateInterval) {
                if (lastUpdateTime != 0L) {
                    val timeDiff = currentTime - lastUpdateTime

                    // Anwendung des Tiefpassfilters auf die Beschleunigungsdaten
                    filteredAcceleration[0] = alpha * filteredAcceleration[0] + (1 - alpha) * event.values[0]
                    filteredAcceleration[1] = alpha * filteredAcceleration[1] + (1 - alpha) * event.values[1]
                    filteredAcceleration[2] = alpha * filteredAcceleration[2] + (1 - alpha) * event.values[2]

                    // Berechne den Betrag der gefilterten Beschleunigung
                    val accelerationMagnitude = sqrt(filteredAcceleration[0].pow(2) + filteredAcceleration[1].pow(2) + filteredAcceleration[2].pow(2))


                    if (accelerationMagnitude < STILL_THRESHOLD) {
                        speed = 0f
                    } else {
                        speed += accelerationMagnitude * timeDiff / 1000
                    }

                    if (startTimeT == 0L) {
                        startTimeT = currentTime
                    } else {
                        totalDistance += speed * timeDiff / 1000
                    }

                    velocity = getCurrentSpeed().toInt()
                    averageVelocity = getAverageVelocity().toInt()
                }
                lastUpdateTime = currentTime
            }
        }
    }

    companion object {
        const val STILL_THRESHOLD = 0.01 // Schwellenwert für Stillstand, anpassen nach
    }

    // Method to calculate the current speed based on the accelerometer data.
    fun getCurrentSpeed(): Double {
        return speed * 3.6 // Convert speed to km/h (if speed is in m/s)
    }
    fun getAverageVelocity(): Double {
        val elapsedTimeMillis = if (startTimeT > 0) System.currentTimeMillis() - startTimeT else 1
        val elapsedTimeSeconds = elapsedTimeMillis / 1000.0 // Umrechnung von Millisekunden in Sekunden
        if (elapsedTimeSeconds == 0.0) return 0.0 // Vermeide Division durch Null
        return (totalDistance / elapsedTimeSeconds) * 3.6 // Umrechnung von m/s in km/h
    }
}
