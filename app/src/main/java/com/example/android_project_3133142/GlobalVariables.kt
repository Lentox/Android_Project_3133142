package com.example.android_project_3133142

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import co.yml.charts.common.model.Point
import com.example.android_project_3133142.controller.Speed
import com.example.android_project_3133142.controller.SpeedUnit
import com.example.android_project_3133142.controller.Vertical
import com.example.android_project_3133142.controller.VerticalUnit
import com.example.android_project_3133142.objects.Altitude
import com.example.android_project_3133142.objects.Slope
import java.util.Timer

// Global Position and Timestamp Variables
var latitude = "0.0" // Current latitude
var longitude = "0.0" // Current longitude
var altitude = "0"    // Current altitude
var timestamp = "2023-01-01 00:00:00 +0000" // Timestamp for the current data
var distance = "0"    // Distance traveled

// Stopwatch and Timer Variables
var isStopwatchRunning = false      // Flag indicating if the stopwatch is running
var elapsedTimeWhenPaused = 0L      // Elapsed time when the stopwatch was paused
var startTime = 0L                  // Start time of the stopwatch
var timer: Timer? = null            // Timer used for updating UI in regular intervals

// Running Stats Variables
var runs = 0                        // Number of runs

// Velocity and Gradient Variables
var velocity = 0                    // Current velocity
var maxVelocity = 0                 // Maximum recorded velocity
var averageVelocity = 0             // Average velocity

// Altitude and Gradient Variables
var oldAltitude = 0                 // Previous altitude
var gradient = 0                    // Slope gradient

// Card Display Variables
var isCardDisplayed = false          // Flag indicating if a card is currently displayed
var currentDisplayedCard: Slope? = null // Currently displayed card (if any)

// List of Recorded Slopes
var slopesArray = mutableListOf(
    Slope(
        id = 1,
        maxVelocity = 30,
        avgVelocity = 20,
        distance = 500,
        altitude = Altitude(max = 100, min = 50, delta = 50),
        runs = 5,
        duration = System.currentTimeMillis().toString(),
        gradient = 10,
        location = "St.Christoph",
        date = "October 30, 2023",
        pointsData = listOf(Point(1f, altitude.toFloat()))
    )
)

// Font Family for Typography
val Ubuntu = FontFamily(
    Font(R.font.ubuntub),
)

// Selected Units for Speed and Vertical Measurements
var selectedSpeedUnit = SpeedUnit(Speed.KMH)
var selectedVerticalUnit = VerticalUnit(Vertical.M)