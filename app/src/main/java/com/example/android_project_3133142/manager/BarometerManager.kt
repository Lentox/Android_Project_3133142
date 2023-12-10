package com.example.android_project_3133142.manager

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import com.example.android_project_3133142.altitude

class BarometerManager(activity: Activity) : SensorEventListener {
    // Obtain the SensorManager from the activity context to interact with the device's sensors.
    private var sensorManager: SensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Retrieve the pressure sensor from the sensor manager.
    private var pressureSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    // Variable to hold the current pressure reading.
    private var currentPressure: Float = 0f

    // Variables to manage the update interval of the sensor readings.
    private var lastUpdate = 0L
    private val updateInterval = 10000L // 10 seconds in milliseconds

    // Initialization block to register the sensor event listener.
    init {
        pressureSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Method to handle changes in sensor accuracy.
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Implement reactions to changes in sensor accuracy if needed.
    }

    // Method to handle sensor data updates.
    override fun onSensorChanged(event: SensorEvent) {
        // Check if the event is from the pressure sensor.
        if (event.sensor.type == Sensor.TYPE_PRESSURE) {
            val currentTime = SystemClock.elapsedRealtime()
            // Update the reading only if the specified interval has passed.
            if ((currentTime - lastUpdate) > updateInterval) {
                lastUpdate = currentTime
                currentPressure = event.values[0] // Update the current pressure reading.
                // Calculate and update the altitude based on the current pressure.
                val altitudeTemp = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, currentPressure)
                altitude = altitudeTemp.toString()
            }
        }
    }

    // Method to unregister the sensor listener and stop receiving updates.
    fun stop() {
        sensorManager.unregisterListener(this)
    }
}
