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

@Composable
fun GridLayout() {

    val item = listOf(
        "Max.Velocity",
        "Avg. Velocity",
        "Distance",
        "Altitude"
    )
    val values = listOf(
        "0  km/h",
        "0    km/h",
        "\n0   km/h\n\n",
        "Max    0m\nMin     0m\nDelta   0m\n"
    )
    LazyVerticalGridWithItems(items = item, values = values, columns = 2)

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
    LazyVerticalGridWithItems(items = item2, values = values2, columns = 3)

    val item3 = listOf(
        "Profile"
    )
    val values3 = listOf(
        "0"
    )
    LazyVerticalGridWithItems(items = item3, values = values3, columns = 1)

    val icon = listOf(
        Icons.Filled.Route,
        Icons.Filled.Dangerous,
        Icons.Filled.PlayCircleOutline,
        Icons.Filled.SaveAlt,
        Icons.Filled.Settings
    )
    LazyVerticalGridWithIcons(
        items = icon,
        columns = 5,
        onIconClick = { index ->
            // Implementation of the Event missing
            println("Icon mit Index $index wurde angeklickt")
        }
    )

}

@Composable
fun LazyVerticalGridWithIcons(
    items: List<ImageVector>,
    columns: Int,
    onIconClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        items(items.size) {item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
                    .background(
                        colorResource(id = R.color.boxBackground),
                        shape = RoundedCornerShape(25)
                    )
                    .clickable { onIconClick(item) }
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = items[item],
                        contentDescription = "Icon",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun LazyVerticalGridWithItems(
    items: List<String>,
    values: List<String>,
    columns: Int,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = false,
        modifier = Modifier
    ) {
        items(items.size) {item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(
                        colorResource(id = R.color.boxBackground),
                        shape = RoundedCornerShape(25)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.tagBackground),
                                shape = RoundedCornerShape(25)
                            )
                            .padding(6.dp)
                            .align(Alignment.CenterHorizontally)
                    ){
                        Text(
                            text = items[item],
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.CenterHorizontally)
                    ){
                        Text(
                            text = values[item],
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}