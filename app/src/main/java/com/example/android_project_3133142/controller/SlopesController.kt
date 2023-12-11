package com.example.android_project_3133142.controller

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.android_project_3133142.currentDisplayedCard
import com.example.android_project_3133142.isCardDisplayed
import com.example.android_project_3133142.objects.Slope

fun displaySlopeCard(slope: Slope){
    isCardDisplayed = true
    currentDisplayedCard = slope
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