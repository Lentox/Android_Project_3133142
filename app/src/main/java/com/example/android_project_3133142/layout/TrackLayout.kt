package com.example.android_project_3133142.layout

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Timer
import java.util.TimerTask
import kotlin.math.round
import androidx.compose.ui.graphics.*
import co.yml.charts.common.model.Point
import com.example.android_project_3133142.objects.Altitude
import com.example.android_project_3133142.manager.MyDatabaseHelper
import com.example.android_project_3133142.objects.Slope
import com.example.android_project_3133142.controller.calculateGradient
import com.example.android_project_3133142.controller.formatElapsedTime
import com.example.android_project_3133142.manager.lastLatitude
import com.example.android_project_3133142.manager.lastLongitude
import com.example.android_project_3133142.manager.startTimeT
import com.example.android_project_3133142.manager.totalDistance
import com.example.android_project_3133142.manager.weatherDataAPI
import com.example.android_project_3133142.altitude
import com.example.android_project_3133142.averageVelocity
import com.example.android_project_3133142.currentDisplayedCard
import com.example.android_project_3133142.distance
import com.example.android_project_3133142.elapsedTimeWhenPaused
import com.example.android_project_3133142.gradient
import com.example.android_project_3133142.isCardDisplayed
import com.example.android_project_3133142.isStopwatchRunning
import com.example.android_project_3133142.maxVelocity
import com.example.android_project_3133142.oldAltitude
import com.example.android_project_3133142.runs
import com.example.android_project_3133142.selectedSpeedUnit
import com.example.android_project_3133142.selectedVerticalUnit
import com.example.android_project_3133142.slopesArray
import com.example.android_project_3133142.startTime
import com.example.android_project_3133142.timer
import com.example.android_project_3133142.velocity

// A Composable function to create a grid layout.
@SuppressLint("ServiceCast")
@Composable
fun GridLayout() {

    val dbHelper = MyDatabaseHelper(context = LocalContext.current)

    var play by remember { mutableStateOf(false) }

    var showDialogSettings by remember { mutableStateOf(false) }
    var showDialogPosition by remember { mutableStateOf(false) }

    var trackOnGoing by remember { mutableStateOf(false) }

    // State for Max, Min and Delta
    var maxAltitude by remember { mutableIntStateOf(0) }
    var minAltitude by remember { mutableIntStateOf(0) }
    var deltaAltitude by remember { mutableIntStateOf(0) }

    var values2 by remember { mutableStateOf(listOf("0", "00:00:00", "0%")) }

    var values by remember { mutableStateOf(listOf("0 km/h", "0 km/h", "\n0 km\n\n", "Max 0m\nMin 0m\nDelta 0m\n")) }

    LaunchedEffect(selectedSpeedUnit, selectedVerticalUnit) {
        values = listOf(
            "0 ${selectedSpeedUnit.unit.label}",
            "0 ${selectedSpeedUnit.unit.label}",
            "\n0 km\n\n",
            "Max 0${selectedVerticalUnit.unit.label}\nMin 0${selectedVerticalUnit.unit.label}\nDelta 0${selectedVerticalUnit.unit.label}\n"
        )
    }

    var pointsData by remember { mutableStateOf(listOf(Point(1f, altitude.toFloat()))) }

    val icon = remember {
        mutableStateListOf(
            Icons.Filled.Route,
            Icons.Filled.Dangerous,
            Icons.Filled.PlayCircleOutline,
            Icons.Filled.SaveAlt,
            Icons.Filled.Settings
        )
    }
    
    // List of labels for grid items.
    val item = listOf(
        "Max. Velocity",
        "Avg. Velocity",
        "Distance",
        "Altitude"
    )

    // Similar to the above but for a third grid with a single column.
    val item3 = listOf(
        "Profile"
    )

    val item2 = listOf(
        "Runs",
        "Duration",
        "Gradient"
    )

    LazyVerticalGridWithItems(items = item, values = values, columns = 2)

    LazyVerticalGridWithItems(items = item2, values = values2, columns = 3)

    LazyVerticalGridWithGraph(items = item3, columns = 1, pointsData)

    fun displayCard(){
        values2 = values2.toMutableList().apply {
            // show Duration
            set(1, currentDisplayedCard?.duration ?: "Loading...")
            // show Gradient
            set(2, "${currentDisplayedCard?.gradient}%")
        }

        values = values.toMutableList().apply {
            // show MaxVelocity
            set(0, currentDisplayedCard?.maxVelocity.toString() + " km/h")
            // show AverageVelocity
            set(1, currentDisplayedCard?.avgVelocity.toString() + " km/h")
            // show distance
            set(2, "\n${currentDisplayedCard?.distance} km\n\n")
            // show Altitude
            set(3, "Max ${currentDisplayedCard?.altitude?.max}m\nMin ${currentDisplayedCard?.altitude?.min}m\nDelta ${currentDisplayedCard?.altitude?.delta}m\n")
        }
    }

    fun pauseStopWatch(){
        // Pause the stopwatch
        elapsedTimeWhenPaused = System.currentTimeMillis() - startTime
        timer?.cancel()
        isStopwatchRunning = false
    }

    fun resumeStopWatch(){
        isCardDisplayed = false
        // Resume or start the stopwatch
        startTime = System.currentTimeMillis() - elapsedTimeWhenPaused
        timer?.cancel()  // Ensure that no timer is already active
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - startTime
                val formattedTime = formatElapsedTime(elapsedTime)

                // update Timer
                values2 = values2.toMutableList().apply {
                    set(1, formattedTime) // Index 1 ist "00:00:00"
                }

                // update Speed
                if (maxVelocity < velocity){
                    maxVelocity = velocity
                }

                values = values.toMutableList().apply {
                    set(0, "$maxVelocity ${selectedSpeedUnit.unit.label}")
                    set(1, "$averageVelocity ${selectedSpeedUnit.unit.label}")
                    set(2, "\n$distance km\n\n")
                }

                // update altitude
                maxAltitude = maxOf(maxAltitude.toDouble().toInt(), altitude.toDouble().toInt())
                if (minAltitude == 0 || minAltitude > altitude.toDouble().toInt()) {
                    minAltitude = altitude.toDouble().toInt()
                }
                deltaAltitude = maxAltitude - minAltitude
                values = values.toMutableList().apply {
                    set(3, "Max ${maxAltitude}${selectedVerticalUnit.unit.label}\nMin ${minAltitude}${selectedVerticalUnit.unit.label}\nDelta ${deltaAltitude}${selectedVerticalUnit.unit.label}\n")
                }

                // update Gradient
                if (oldAltitude == 0){
                    oldAltitude = round(altitude.toDouble()).toInt()
                }

                if (distance.toInt() != 0 && oldAltitude != round(altitude.toDouble()).toInt()){

                    val heightDiff: Int = if (oldAltitude > round(altitude.toDouble()).toInt()){
                        oldAltitude - round(altitude.toDouble()).toInt()
                    }else{
                        round(altitude.toDouble()).toInt() - oldAltitude
                    }

                    val (slopePercentage) = calculateGradient(heightDiff.toDouble(), distance.toDouble())

                    val gradientPerc = round(slopePercentage).toInt()

                    if (gradient < gradientPerc){
                        values2 = values2.toMutableList().apply {
                            set(2, "$gradientPerc%")
                        }
                        gradient = gradientPerc
                    }
                    oldAltitude = round(altitude.toDouble()).toInt()

                    //pointsData.add(Point((pointsData.size + 1).toFloat(), altitude.toFloat()))
                    pointsData += Point((pointsData.size + 1).toFloat(), altitude.toFloat())
                }
            }
        }, 0, 1000) // Update every second
        isStopwatchRunning = true
    }

    fun onPlayButtonClick() {
        if (isStopwatchRunning) {
            pauseStopWatch()
        } else {
            resumeStopWatch()
        }

        if (!play) {
            // update Runs
            runs++
            values2 = values2.toMutableList().apply {
                set(0, runs.toString())
            }
        }

        play = !play
        trackOnGoing = true
        // Changes the Icon of the Play Button to a Pause Button
        icon[2] = if (play) Icons.Filled.PauseCircle else Icons.Filled.PlayCircle

    }

    fun resetValues(){
        play = false
        trackOnGoing = false

        timer?.cancel()
        isStopwatchRunning = false
        elapsedTimeWhenPaused = 0L
        startTime = 0L

        values2 = values2.toMutableList().apply {
            set(2, "0%")
            set(1, "00:00:00")
            set(0, "0")
        }
        runs = 0
        values = values.toMutableList().apply {
            set(3, "Max 0${selectedVerticalUnit.unit.label}\nMin 0${selectedVerticalUnit.unit.label}\nDelta 0${selectedVerticalUnit.unit.label}\n")
            set(2, "\n0 km\n\n")
            set(1, "0 ${selectedSpeedUnit.unit.label}")
            set(0, "0 ${selectedSpeedUnit.unit.label}")
        }
        totalDistance = 0f
        startTimeT = 0L
        maxVelocity = 0
        distance = "0"
        lastLatitude = 0.0
        lastLongitude = 0.0
        maxAltitude = 0
        minAltitude = 0
        deltaAltitude = 0
        gradient = 0

        pointsData = mutableListOf(Point(1f, altitude.toFloat()))
    }

    fun onDeleteButtonClick() {
        if (isCardDisplayed){
            // Delete displayed card
            isCardDisplayed = false
            for (slope in slopesArray){
                if (slope == currentDisplayedCard){
                    dbHelper.deleteSlope(slope)
                    slopesArray.remove(slope)
                }
            }
            reCalcStatistic()
            resetValues()
        }else{
            // Delete current track
            resetValues()
        }
    }

    fun onSaveButtonClick() {

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

        val formattedDate = currentDate.format(formatter)

        // Save Data into SlopesArray
        val altitudeObj = Altitude(max = maxAltitude, min = minAltitude, delta = deltaAltitude)
        val slopeObj = Slope(
            id = (slopesArray.size + 1).toLong(),
            maxVelocity = maxVelocity,
            avgVelocity = averageVelocity,
            distance = distance.toInt(),
            altitude = altitudeObj,
            runs = runs,
            duration =  formatElapsedTime(System.currentTimeMillis() - startTime),
            gradient = gradient,
            location = weatherDataAPI?.location ?: "Loading...",
            date = formattedDate,
            pointsData = pointsData
        )
        slopesArray.add(slopeObj)

        dbHelper.insertSlope(slopeObj)

        reCalcStatistic()

        resetValues()
    }

    if (isCardDisplayed){
        displayCard()
    }

    // Creates a lazy vertical grid of icons with 5 columns.
    LazyVerticalGridWithIcons(
        items = icon,
        columns = 5,
        play = play,
        trackOnGoing,
        onIconClick = { index ->
            when(index){
                0 -> showDialogPosition = !showDialogPosition
                1 -> onDeleteButtonClick()
                2 -> onPlayButtonClick()
                3 -> onSaveButtonClick()
                4 -> showDialogSettings = !showDialogSettings
            }
        }
    )
    if (showDialogPosition) {
        PopupDialog(
            onDismiss = { showDialogPosition = false },
            index = 0
        )
    }else if (showDialogSettings){
        PopupDialog(
            onDismiss = { showDialogSettings = false },
            index = 1
        )
    }
}