package com.example.android_project_3133142

import co.yml.charts.common.model.Point
import com.example.android_project_3133142.objects.Altitude
import com.example.android_project_3133142.objects.Slope
import java.util.Timer

// Global variables
var latitude = "-6.278581"
var longitude = "-6.278581"
var altitude = "23"
var timestamp = "2023-11-19 20:03:42 +0000"
var distance = "0"

var isStopwatchRunning = false
var elapsedTimeWhenPaused = 0L
var startTime = 0L
var timer: Timer? = null

var runs = 0

var velocity = 0
var maxVelocity = 0
var averageVelocity = 0

var oldAltitude = 0
var gradient = 0

var isCardDisplayed = false
var currentDisplayedCard: Slope? = null

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
        listOf(Point(1f, altitude.toFloat()))
    )
)