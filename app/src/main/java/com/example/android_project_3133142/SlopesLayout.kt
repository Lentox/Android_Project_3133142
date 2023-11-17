package com.example.android_project_3133142

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Composable function to display a scrollable list of cards.
@Composable
fun ScrollableCardList(items: List<CardData>, onIconClick: (Int) -> Unit) {
    // LazyColumn creates a vertically scrollable column that only composes and lays out the currently visible items.
    LazyColumn (
        modifier = Modifier
            .height(605.dp) // Explicit height of the LazyColumn.
    ){
        // Creates a list of items based on the 'items' list passed to the function.
        itemsIndexed(items) { index, item ->
            // Card composable to display each item.
            Card(
                modifier = Modifier
                    .fillMaxWidth() // Fills the maximum width available.
                    .padding(vertical = 10.dp, horizontal = 20.dp), // Padding around the card.
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Elevation of the card for shadow effect.
            ) {
                // Row to arrange the image, text, and icon button horizontally.
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Fills the maximum width available.
                        .padding(8.dp), // Padding inside the row.
                    verticalAlignment = Alignment.CenterVertically // Vertically centers the row's children.
                ) {
                    // Image representing the item.
                    Image(
                        painter = painterResource(id = item.imageRes), // Image resource.
                        contentDescription = null, // Accessibility description.
                        modifier = Modifier.size(100.dp) // Size of the image.
                            .clip(RoundedCornerShape(8.dp)), // Rounded corners of the image.
                        contentScale = ContentScale.Crop // Scaling of the image.
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Spacer for horizontal spacing.

                    // Column to arrange text vertically.
                    Column(
                        modifier = Modifier.weight(1f) // Column takes up the remaining space.
                    ) {
                        // Box for the location text.
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp)) // Rounded corners for the box.
                                .background(Color.Blue) // Background color of the box.
                        ) {
                            // Text displaying the location.
                            Text(
                                modifier = Modifier
                                    .padding(6.dp), // Padding inside the text box.
                                text = item.location,
                                color = Color.White // Text color.
                            )
                        }
                        // Iterating over the details list to display icon and text pairs.
                        item.details.forEach { detail ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically // Vertically centers the row's children.
                            ) {
                                // Icon for the detail.
                                Icon(
                                    imageVector = detail.icon, // Icon vector.
                                    contentDescription = null // Accessibility description.
                                )
                                Spacer(modifier = Modifier.width(4.dp)) // Spacer for horizontal spacing.
                                // Text corresponding to the icon.
                                Text(text = detail.text)
                            }
                        }
                        // Text displaying the date.
                        Text(
                            text = item.date,
                            color = Color.Gray, // Text color.
                            style = MaterialTheme.typography.titleSmall // Text style.
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // Spacer for horizontal spacing.

                    // Icon button to handle click events.
                    IconButton(onClick = { onIconClick(index) }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert, // Icon vector.
                            contentDescription = "Mehr Optionen" // Accessibility description.
                        )
                    }
                }
            }
        }
    }
}

// Data class representing the data for a card.
data class CardData(
    val imageRes: Int, // Resource ID of the image.
    val location: String, // Location text.
    val details: List<IconTextPair>, // List of icon-text pairs.
    val date: String // Date text.
)

// Data class representing an icon and a text.
data class IconTextPair(
    val icon: ImageVector, // Icon vector.
    val text: String // Corresponding text.
)
