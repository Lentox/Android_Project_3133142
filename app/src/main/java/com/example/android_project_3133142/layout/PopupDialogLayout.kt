package com.example.android_project_3133142.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.android_project_3133142.R
import com.example.android_project_3133142.Ubuntu
import com.example.android_project_3133142.altitude
import com.example.android_project_3133142.controller.Speed
import com.example.android_project_3133142.controller.SpeedUnit
import com.example.android_project_3133142.controller.UnitType
import com.example.android_project_3133142.controller.Vertical
import com.example.android_project_3133142.controller.VerticalUnit
import com.example.android_project_3133142.latitude
import com.example.android_project_3133142.longitude
import com.example.android_project_3133142.selectedSpeedUnit
import com.example.android_project_3133142.selectedVerticalUnit
import com.example.android_project_3133142.timestamp

@Composable
fun PopupDialog(
    onDismiss: () -> Unit,
    index: Int
) {
    Dialog(onDismissRequest = onDismiss) {

        if (index == 0){ // Position Dialog
            PopupDialogPosition(onDismiss)
        }else if (index == 1){ // Settings Dialog
            PopupDialogSettings(onDismiss)
        }
    }
}
@Composable
fun PopupDialogSettings(onDismiss: () -> Unit) {

    var selectedSpeedState by remember { mutableStateOf(selectedSpeedUnit) }
    var selectedVerticalState by remember { mutableStateOf(selectedVerticalUnit) }

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .fillMaxHeight(fraction = 0.40f)
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
                    "Settings",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                    fontFamily = Ubuntu
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
                UnitSwitch(
                    speedUnit = selectedSpeedState,
                    verticalUnit = selectedVerticalState,
                    onSpeedUnitSelected = { newSpeedUnit ->
                        selectedSpeedState = newSpeedUnit
                        selectedSpeedUnit = newSpeedUnit
                    },
                    onVerticalUnitSelected = { newVerticalUnit ->
                        selectedVerticalState = newVerticalUnit
                        selectedVerticalUnit = newVerticalUnit
                    },
                )
            }
        }
    }
}

@Composable
fun UnitSwitch(
    speedUnit: SpeedUnit,
    verticalUnit: VerticalUnit,
    onSpeedUnitSelected: (SpeedUnit) -> Unit,
    onVerticalUnitSelected: (VerticalUnit) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
            .background(
                colorResource(id = R.color.tagBackground),
                shape = RoundedCornerShape(25)
            )
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        UnitSwitchItem(
            unit = SpeedUnit(Speed.KMH),
            isSelected = speedUnit == SpeedUnit(Speed.KMH),
            onSelected = { onSpeedUnitSelected(SpeedUnit(Speed.KMH)) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        UnitSwitchItem(
            unit = SpeedUnit(Speed.MPH),
            isSelected = speedUnit == SpeedUnit(Speed.MPH),
            onSelected = { onSpeedUnitSelected(SpeedUnit(Speed.MPH)) }
        )
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
            .background(
                colorResource(id = R.color.tagBackground),
                shape = RoundedCornerShape(25)
            )
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        UnitSwitchItem(
            unit = VerticalUnit(Vertical.M),
            isSelected = verticalUnit == VerticalUnit(Vertical.M),
            onSelected = { onVerticalUnitSelected(VerticalUnit(Vertical.M)) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        UnitSwitchItem(
            unit = VerticalUnit(Vertical.FT),
            isSelected = verticalUnit == VerticalUnit(Vertical.FT),
            onSelected = { onVerticalUnitSelected(VerticalUnit(Vertical.FT)) }
        )
    }

}

@Composable
fun UnitSwitchItem(
    unit: UnitType,
    isSelected: Boolean,
    onSelected: (UnitType) -> Unit
) {
    Box(
        modifier = Modifier
            .background(if (isSelected) colorResource(id = R.color.purple_500) else Color.Transparent)
            .clickable { onSelected(unit) }
            .padding(10.dp)
    ) {
        Text(
            text = when (unit) {
                is SpeedUnit -> when (unit.unit) {
                    Speed.KMH -> "km/h"
                    Speed.MPH -> "mph"
                }
                is VerticalUnit -> when (unit.unit) {
                    Vertical.M -> "m"
                    Vertical.FT -> "ft"
                }
            },
            color = Color.Black,
            fontFamily = Ubuntu
        )
    }
}

@Composable
fun PopupDialogPosition(onDismiss: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .fillMaxHeight(fraction = 0.40f)
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
                    "Position",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                    fontFamily = Ubuntu
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
                TextItem(label = "Latitude", value = latitude)
                TextItem(label = "Longitude", value = longitude)
                TextItem(label = "Altitude", value = altitude + "m")
                TextItem(label = "Timestamp", value = timestamp)
            }
        }
    }
}

@Composable
fun TextItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            fontFamily = Ubuntu
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = Ubuntu
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer for vertical spacing.
    }
}