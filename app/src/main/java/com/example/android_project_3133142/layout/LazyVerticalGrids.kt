package com.example.android_project_3133142.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point
import com.example.android_project_3133142.R
import com.example.android_project_3133142.Ubuntu
import com.example.android_project_3133142.currentDisplayedCard
import com.example.android_project_3133142.isCardDisplayed

@Composable
fun LazyVerticalGridWithIcons(
    items: List<ImageVector>, // List of icons to be displayed.
    columns: Int, // Number of columns in the grid.
    play: Boolean,
    trackOnGoing: Boolean,
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
                            || ((item == 0 || item == 1 || item == 2) && isCardDisplayed)
                        )
                            colorResource(id = R.color.boxBackground) else Color.Gray, // Background color of the element.
                        shape = RoundedCornerShape(25) // Rounded corners for the element.
                    )
                    .then(
                        if (((item == 0 || item == 2 || item == 4) && !play && !trackOnGoing && !isCardDisplayed)
                            || ((item == 0 || item == 2 || item == 4) && trackOnGoing && !isCardDisplayed)
                            || ((item == 1 || item == 3) && trackOnGoing && !play && !isCardDisplayed)
                            || ((item == 0 || item == 1 || item == 2) && isCardDisplayed)
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
                            fontSize = 19.sp, // Text size.
                            fontFamily = Ubuntu
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(4.dp) // Padding between the label and value boxes.
                            .align(Alignment.CenterHorizontally) // Aligns the value box horizontally in the center.
                    ){
                        Text(
                            text = values[item], // Value text.
                            color = colorResource(id = R.color.trackValueFontColor), // Text color.
                            fontSize = 20.sp, // Text size.
                            fontFamily = Ubuntu,
                            fontWeight = FontWeight.Medium
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
    columns: Int, // Number of columns in the grid.
    pointsData: List<Point>
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
                            fontSize = 20.sp, // Text size.
                            fontFamily = Ubuntu
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(4.dp) // Padding between the label and value boxes.
                            .align(Alignment.CenterHorizontally) // Aligns the value box horizontally in the center.
                            .fillMaxWidth() // Make sure that the box container is wide enough
                            .height(130.dp)
                    ){
                        Column {
                            if (isCardDisplayed){
                                currentDisplayedCard?.let { LineChartScreen(pointsData = it.pointsData) }
                            }else{
                                LineChartScreen(pointsData)
                            }
                        }
                    }
                }
            }
        }
    }
}