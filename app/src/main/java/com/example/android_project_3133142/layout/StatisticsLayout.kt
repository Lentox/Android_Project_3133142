package com.example.android_project_3133142.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_project_3133142.R
import com.example.android_project_3133142.Ubuntu

// Data class representing a statistic, consisting of a label, a value, and an icon.
data class Statistic(
    val label: String,
    val value: String,
    val icon: ImageVector
)

// Composable function to display a single statistic.
@Composable
fun StatisticBox(statistic: Statistic) {
    // Container box for the statistic.
    Box(
        modifier = Modifier
            .fillMaxWidth() // Fills the maximum width available.
            .padding(16.dp) // Padding around the box.
    ) {
        // Row layout to arrange icon and text horizontally.
        Row(
            verticalAlignment = Alignment.CenterVertically, // Centers children vertically.
            horizontalArrangement = Arrangement.Center, // Centers children horizontally.
            modifier = Modifier.fillMaxWidth() // Fills the maximum width available.
        ) {
            // Icon representing the statistic.
            Icon(
                imageVector = statistic.icon,
                contentDescription = "Statistic Icon",
                modifier = Modifier.size(24.dp) // Sets the size of the icon.
            )
            // Column layout to arrange texts vertically.
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally.
                modifier = Modifier.weight(1f) // Fills the available space.
            ) {
                // Text showing the label of the statistic.
                Text(
                    text = statistic.label,
                    color = colorResource(id = R.color.tagBackground),
                    fontWeight = FontWeight.Bold, // Bold font weight.
                    textAlign = TextAlign.Center, // Text aligned to center.
                    fontSize = 17.sp,
                    fontFamily = Ubuntu
                )
                // Text showing the value of the statistic.
                Text(
                    text = statistic.value,
                    textAlign = TextAlign.Center, // Text aligned to center.
                    fontSize = 15.sp,
                    fontFamily = Ubuntu
                )
                Spacer(modifier = Modifier.height(6.dp)) // Spacer for horizontal spacing.
            }
        }
    }
}

// Composable function to display a list of statistics.
@Composable
fun ProfileStatisticsView(statistics: List<Statistic>) {
    // Container box for the whole statistics view.
    Box(
        modifier = Modifier
            .fillMaxWidth() // Fills the maximum width available.
            .padding(16.dp) // Padding around the box.
            .clip(RoundedCornerShape(12.dp)) // Rounded corners for the box.
            .background(colorResource(id = R.color.whiteBackgroundBox)) // Semi-transparent white background.
            .padding(8.dp) // Additional padding inside the box.
    ) {
        // Column layout to arrange statistic boxes vertically.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally.
            verticalArrangement = Arrangement.spacedBy(16.dp), // Spacing between children.
            modifier = Modifier.fillMaxWidth() // Fills the maximum width available.
        ) {
            // Iterates over the list of statistics and creates a box for each.
            statistics.forEach { statistic ->
                StatisticBox(statistic)
            }
        }
    }
}




