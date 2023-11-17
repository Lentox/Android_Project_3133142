package com.example.android_project_3133142

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// A Composable function to create a grid layout.
@Composable
fun GridLayout() {

    // List of labels for grid items.
    val item = listOf(
        "Max.Velocity",
        "Avg. Velocity",
        "Distance",
        "Altitude"
    )
    // List of corresponding values for each grid item.
    val values = listOf(
        "0 km/h",
        "0 km/h",
        "\n0 km/h\n\n",
        "Max 0m\nMin 0m\nDelta 0m\n"
    )
    // Creates a lazy vertical grid with 2 columns using the above items and values.
    LazyVerticalGridWithItems(items = item, values = values, columns = 2)

    // Similar to the above but with different items and values for a second grid.
    val item2 = listOf(
        "Runs",
        "Duration",
        "Pitch"
    )
    val values2 = listOf(
        "0",
        "00:00:00",
        "0°"
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
    LazyVerticalGridWithItems(items = item3, values = values3, columns = 1)

    // List of icons.
    val icon = listOf(
        Icons.Filled.Route,
        Icons.Filled.Dangerous,
        Icons.Filled.PlayCircleOutline,
        Icons.Filled.SaveAlt,
        Icons.Filled.Settings
    )
    // Creates a lazy vertical grid of icons with 5 columns.
    LazyVerticalGridWithIcons(
        items = icon,
        columns = 5,
        onIconClick = { index ->
            // Placeholder for icon click handling. Currently prints the index of the clicked icon.
            println("Icon mit Index $index wurde angeklickt")
        }
    )
}

// Composable function that creates a lazy vertical grid of icons.
@Composable
fun LazyVerticalGridWithIcons(
    items: List<ImageVector>, // List of icons to be displayed.
    columns: Int, // Number of columns in the grid.
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
                        colorResource(id = R.color.boxBackground), // Background color of the item.
                        shape = RoundedCornerShape(25) // Rounded corners for the item.
                    )
                    .clickable { onIconClick(item) } // Makes the item clickable.
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
