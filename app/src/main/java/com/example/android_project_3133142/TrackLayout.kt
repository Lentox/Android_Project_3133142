package com.example.android_project_3133142

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Timer
import java.util.TimerTask
import kotlin.math.atan
import kotlin.math.round

var latitude = "-6.278581"
var longtitude = "-6.278581"
var altitude = "23 m"
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

var slopesArray = mutableListOf<Slope>(
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
        date = "Oktober 30, 2023"
    )
)

// A Composable function to create a grid layout.
@SuppressLint("ServiceCast")
@Composable
fun GridLayout() {

    var play by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var trackOnGoing by remember { mutableStateOf(false) }

    // Zustände für Max, Min und Delta
    var maxAltitude by remember { mutableStateOf(0) }
    var minAltitude by remember { mutableStateOf(0) }
    var deltaAltitude by remember { mutableStateOf(0) }

    var values2 by remember { mutableStateOf(listOf("0", "00:00:00", "0%")) }

    var values by remember { mutableStateOf(listOf("0 km/h", "0 km/h", "\n0 km\n\n", "Max 0m\nMin 0m\nDelta 0m\n")) }

    val dbHelper = MyDatabaseHelper(context = LocalContext.current)

    var icon = remember {
        mutableStateListOf(
            Icons.Filled.Route,
            Icons.Filled.Dangerous,
            Icons.Filled.PlayCircleOutline,
            Icons.Filled.SaveAlt,
            Icons.Filled.Settings
        )
    }

    @Composable
    fun LazyVerticalGridWithIcons(
        items: List<ImageVector>, // List of icons to be displayed.
        columns: Int, // Number of columns in the grid.
        play: Boolean,
        onIconClick: (Int) -> Unit // Function to be called when an icon is clicked.
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns), // Fixed number of columns.
            userScrollEnabled = false, // Scrolling is disabled.
            modifier = Modifier
                .padding(top = 30.dp, bottom = 10.dp) // Padding applied to the grid.
        ) {
            items(items.size) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Fills the maximum size available.
                        .padding(start = 10.dp, end = 10.dp) // Padding for each item.
                        .background(
                            if (((item == 0 || item == 2 || item == 4) && !play && !trackOnGoing && !isCardDisplayed)
                                || ((item == 0 || item == 2 || item == 4) && trackOnGoing && !isCardDisplayed)
                                || ((item == 1 || item == 3) && trackOnGoing && !play && !isCardDisplayed)
                                || ((item == 0 || item == 1 || item == 2)  && isCardDisplayed)
                            )
                                colorResource(id = R.color.boxBackground) else Color.Gray, // Hintergrundfarbe des Elements.
                            shape = RoundedCornerShape(25) // Abgerundete Ecken für das Element.
                        )
                        .then(
                            if (((item == 0 || item == 2 || item == 4) && !play && !trackOnGoing && !isCardDisplayed)
                                || ((item == 0 || item == 2 || item == 4) && trackOnGoing && !isCardDisplayed)
                                || ((item == 1 || item == 3) && trackOnGoing && !play && !isCardDisplayed)
                                || ((item == 0 || item == 1 || item == 2)  && isCardDisplayed)
                            )
                                Modifier.clickable { onIconClick(item) } else Modifier
                        )

                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp) // Padding inside the item.
                            .align(Alignment.Center) // Aligns the content to the center.
                    ) {
                        Icon(
                            imageVector = items[item],
                            contentDescription = "Icon",
                            tint = Color.Black // Icon color.
                        )
                    }
                }
            }
        }
    }

    // Composable function that creates a lazy vertical grid of items with labels and values.
    @Composable
    fun LazyVerticalGridWithItems(
        items: List<String>, // List of item labels.
        values: List<String>, // List of item values.
        columns: Int, // Number of columns in the grid.
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns), // Fixed number of columns.
            horizontalArrangement = Arrangement.spacedBy(5.dp), // Horizontal spacing between items.
            verticalArrangement = Arrangement.spacedBy(10.dp), // Vertical spacing between items.
            userScrollEnabled = false, // Scrolling is disabled.
            modifier = Modifier.padding(horizontal = 12.dp) // Padding applied to the grid.
        ) {
            items(items.size) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Fills the maximum size available.
                        .padding(8.dp) // Padding for each item.
                        .background(
                            colorResource(id = R.color.boxBackground), // Background color of the item.
                            shape = RoundedCornerShape(8.dp) // Rounded corners for the item.
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp) // Padding inside the item.
                            .align(Alignment.Center) // Aligns the content to the center.
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    colorResource(id = R.color.tagBackground), // Background color of the label box.
                                    shape = RoundedCornerShape(8.dp) // Rounded corners for the label box.
                                )
                                .padding(6.dp) // Padding inside the label box.
                                .align(Alignment.CenterHorizontally) // Aligns the label box horizontally in the center.
                        ){
                            Text(
                                text = items[item], // Label text.
                                color = Color.White, // Text color.
                                fontSize = 20.sp // Text size.
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(4.dp) // Padding between the label and value boxes.
                                .align(Alignment.CenterHorizontally) // Aligns the value box horizontally in the center.
                        ){
                            Text(
                                text = values[item], // Value text.
                                color = Color.Black, // Text color.
                                fontSize = 20.sp // Text size.
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LazyVerticalGridWithGraph(
        items: List<String>, // List of item labels.
        values: List<String>, // List of item values.
        columns: Int, // Number of columns in the grid.
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns), // Fixed number of columns.
            horizontalArrangement = Arrangement.spacedBy(5.dp), // Horizontal spacing between items.
            verticalArrangement = Arrangement.spacedBy(10.dp), // Vertical spacing between items.
            userScrollEnabled = false, // Scrolling is disabled.
            modifier = Modifier.padding(horizontal = 12.dp) // Padding applied to the grid.
        ) {
            items(items.size) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Fills the maximum size available.
                        .padding(8.dp) // Padding for each item.
                        .background(
                            colorResource(id = R.color.boxBackground), // Background color of the item.
                            shape = RoundedCornerShape(8.dp) // Rounded corners for the item.
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp) // Padding inside the item.
                            .align(Alignment.Center) // Aligns the content to the center.
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    colorResource(id = R.color.tagBackground), // Background color of the label box.
                                    shape = RoundedCornerShape(8.dp) // Rounded corners for the label box.
                                )
                                .padding(6.dp) // Padding inside the label box.
                                .align(Alignment.CenterHorizontally) // Aligns the label box horizontally in the center.
                        ){
                            Text(
                                text = items[item], // Label text.
                                color = Color.White, // Text color.
                                fontSize = 20.sp // Text size.
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(4.dp) // Padding between the label and value boxes.
                                .align(Alignment.CenterHorizontally) // Aligns the value box horizontally in the center.
                                .fillMaxWidth() // Stellen Sie sicher, dass der Box-Container breit genug ist
                                .height(130.dp)
                        ){
                            Column {
                                GraphWithHeightAndTimeAxes(
                                    heights = listOf(10f, 2.5f, 1.8f, 3.0f,10f, 2.5f, 1.8f, 3.0f,10f, 2.5f, 1.8f, 3.0f),
                                    timestamps = listOf("0", "5", "10", "15", "20", "25", "30")
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // List of labels for grid items.
    val item = listOf(
        "Max. Velocity",
        "Avg. Velocity",
        "Distance",
        "Altitude"
    )

    // Creates a lazy vertical grid with 2 columns using the above items and values.
    LazyVerticalGridWithItems(items = item, values = values, columns = 2)

    // Similar to the above but with different items and values for a second grid.
    val item2 = listOf(
        "Runs",
        "Duration",
        "Gradient"
    )

    // Creates a lazy vertical grid with 3 columns using the above items and values.
    LazyVerticalGridWithItems(items = item2, values = values2, columns = 3)

    // Similar to the above but for a third grid with a single column.
    val item3 = listOf(
        "Profile"
    )
    val values3 = listOf(
        "0"
    )

    LazyVerticalGridWithGraph(items = item3, values = values3, columns = 1)

    if (isCardDisplayed){
        // show Timer
        values2 = values2.toMutableList().apply {
            set(1, currentDisplayedCard?.duration ?: "Loading...") // Index 1 ist "00:00:00"
        }
        // show MaxVelocity
        values = values.toMutableList().apply {
            set(0, currentDisplayedCard?.maxVelocity.toString() + " km/h")
        }
        // show AverageVelocity
        values = values.toMutableList().apply {
            set(1, currentDisplayedCard?.avgVelocity.toString() + " km/h")
        }
        // show Altitude
        values = values.toMutableList().apply {
            set(3, "Max ${currentDisplayedCard?.altitude?.max}m\nMin ${currentDisplayedCard?.altitude?.min}m\nDelta ${currentDisplayedCard?.altitude?.delta}m\n")
        }

        // show distance
        values = values.toMutableList().apply {
            set(2, "\n${currentDisplayedCard?.distance} km\n\n")
        }

        // show Gradient
        values2 = values2.toMutableList().apply {
            set(2, "${currentDisplayedCard?.gradient}%")
        }
    }

    fun onPlayButtonClick() {
        if (isStopwatchRunning) {
            // Pause the stopwatch
            elapsedTimeWhenPaused = System.currentTimeMillis() - startTime
            timer?.cancel()
            isStopwatchRunning = false
        } else {
            isCardDisplayed = false
            // Resume or start the stopwatch
            startTime = System.currentTimeMillis() - elapsedTimeWhenPaused
            timer?.cancel()  // Sicherstellen, dass kein Timer bereits läuft
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
                        set(0, "$maxVelocity km/h")
                    }
                    values = values.toMutableList().apply {
                        set(1, "$averageVelocity km/h")
                    }
                    // update altitude
                    maxAltitude = maxOf(maxAltitude.toDouble().toInt(), altitude.toDouble().toInt())
                    if (minAltitude == 0 || minAltitude > altitude.toDouble().toInt()) {
                        minAltitude = altitude.toDouble().toInt()
                    }
                    deltaAltitude = maxAltitude - minAltitude
                    values = values.toMutableList().apply {
                        set(3, "Max ${maxAltitude}m\nMin ${minAltitude}m\nDelta ${deltaAltitude}m\n")
                    }

                    // update distance
                    values = values.toMutableList().apply {
                        set(2, "\n${distance} km\n\n")
                    }

                    // update Gradient
                    if (oldAltitude == 0){
                        oldAltitude = round(altitude.toDouble()).toInt()
                    }

                    if (distance.toInt() != 0 && oldAltitude != round(altitude.toDouble()).toInt()){

                        var heightDiff = 0

                        heightDiff = if (oldAltitude > round(altitude.toDouble()).toInt()){
                            oldAltitude - round(altitude.toDouble()).toInt()
                        }else{
                            round(altitude.toDouble()).toInt() - oldAltitude
                        }

                        val (slopePercentage, slopeAngle) = calculateGradient(heightDiff.toDouble(), distance.toDouble())

                        var gradientPerc = round(slopePercentage).toInt()

                        if (gradient < gradientPerc){
                            values2 = values2.toMutableList().apply {
                                set(2, "$gradientPerc%")
                            }
                            gradient = gradientPerc
                        }
                        oldAltitude = round(altitude.toDouble()).toInt()
                    }

                    //altitudeValues.add(round(altitude.toDouble()).toInt().toString())
                }
            }, 0, 1000) // Aktualisierung jede Sekunde
            isStopwatchRunning = true
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

        // Werte zurücksetzen
        values2 = values2.toMutableList().apply {
            set(1, "00:00:00")
            set(0, "0")
        }
        runs = 0
        values = values.toMutableList().apply {
            set(3, "Max 0m\nMin 0m\nDelta 0m\n")
            set(2, "\n0 km\n\n")
            set(1, "0 km/h")
            set(0, "0 km/h")
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
            location = "${weatherDataAPI?.location ?: "Loading..."}",
            date = formattedDate
        )
        slopesArray.add(slopeObj)
        println("Saved Index: " + (slopesArray.size).toLong())
        dbHelper.insertSlope(slopeObj)

        reCalcStatistic()

        resetValues()
    }


    // Creates a lazy vertical grid of icons with 5 columns.
    LazyVerticalGridWithIcons(
        items = icon,
        columns = 5,
        play = play,
        onIconClick = { index ->
            when(index){
                0 -> showDialog = !showDialog
                1 -> onDeleteButtonClick()
                2 -> onPlayButtonClick()
                3 -> onSaveButtonClick()
                4 -> onSettingsButtonClick()
            }
        }
    )
    if (showDialog) {
        MyPopupDialog(
            onDismiss = { showDialog = false },
        )
    }
}

fun displaySlopeCard(slope: Slope){
    isCardDisplayed = true
    currentDisplayedCard = slope
}

fun calculateGradient(heightDifference: Double, horizontalDistance: Double): Pair<Double, Double> {
    if (horizontalDistance == 0.0) {
        throw IllegalArgumentException("Horizontale Entfernung kann nicht 0 sein.")
    }
    val slopePercentage = (heightDifference / horizontalDistance) * 100
    val slopeAngle = Math.toDegrees(atan(heightDifference / horizontalDistance))
    return Pair(slopePercentage, slopeAngle)
}

fun onSettingsButtonClick() {
    TODO("Not yet implemented")
}

fun formatElapsedTime(elapsedTime: Long): String {
    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = elapsedTime / (1000 * 60 * 60)

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun MyPopupDialog(
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .fillMaxHeight(fraction = 0.45f)
                .padding(20.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp, start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Positionstatus",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Spacer(modifier = Modifier.height(16.dp))

                    // Inhalte zentriert in der Column
                    TextItem(label = "latitude", value = latitude)
                    TextItem(label = "longtitude", value = longtitude)
                    TextItem(label = "Altitude", value = altitude + "m")
                    TextItem(label = "timestamp", value = timestamp)
                    TextItem(label = "Vertical Accuracy", value = "± 12 m")
                    TextItem(label = "Horizontal Accuracy", value = "± 35 m")
                }
            }
        }
    }
}

@Composable
fun TextItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally // Zentriert den Inhalt horizontal
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun GraphWithHeightAndTimeAxes(heights: List<Float>, timestamps: List<String>) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(16.dp)) {

        val axisPaint = Paint().apply {
            textSize = 30f
        }

        // Zeichnen der X-Achse
        drawLine(
            color = Color.Black,
            start = Offset(40f, size.height - 40f),
            end = Offset(size.width, size.height - 40f),
            strokeWidth = Stroke.DefaultMiter
        )

        // Zeichnen der Y-Achse
        drawLine(
            color = Color.Black,
            start = Offset(40f, 0f),
            end = Offset(40f, size.height - 40f),
            strokeWidth = Stroke.DefaultMiter
        )

        // Berechnung der Schritte für X- und Y-Achse
        val stepX = (size.width - 40f) / (timestamps.size - 1)
        val maxHeight = heights.maxOrNull() ?: 1f
        val stepY = (size.height - 40f) / maxHeight

        // Zeichnen der Datenpunkte und Verbindungslinien
        var previousPoint = Offset(40f, size.height - 40f - (heights.first() * stepY))
        heights.zip(timestamps).forEachIndexed { index, (height, _) ->
            val x = 40f + stepX * index
            val y = size.height - 40f - (height * stepY)
            val currentPoint = Offset(x, y)

            // Zeichnen der Verbindungslinie
            if (index > 0) {
                drawLine(
                    start = previousPoint,
                    end = currentPoint,
                    color = Color.Blue,
                    strokeWidth = 5f
                )
            }
            previousPoint = currentPoint

            // Hier passen wir den Abstand der Texte an
            val textOffsetY = y - axisPaint.textSize * index // Multiplizieren Sie den Index mit der Textgröße, um den Abstand zu erhöhen

            // Zeichnen der Beschriftungen
            drawContext.canvas.nativeCanvas.apply {
                // X-Achse (Zeitstempel)
                drawText(timestamps[index], x - axisPaint.textSize / 2, size.height - 10f, axisPaint)
                // Y-Achse (Höhe)
                if (index == 0){
                    drawText(height.toString(), -30f, y + axisPaint.textSize / 2, axisPaint)
                    drawText((height / 2).toString(), -30f, y + 125, axisPaint)
                }
            }
        }
    }
}
