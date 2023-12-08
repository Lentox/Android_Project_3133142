package com.example.android_project_3133142

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.io.FileNotFoundException
import java.io.IOException

// MainActivity class which extends ComponentActivity, the base class for activities in Jetpack Compose.
class MainActivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager
    private  lateinit var barometerManager: BarometerManager
    private lateinit var accelerometerManager: AccelerometerManager

    private lateinit var dbHelper: MyDatabaseHelper
    // Suppresses Lint warnings for the unused Scaffold padding parameter.
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    // Marks that this code uses experimental features of Material3.
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = MyDatabaseHelper(this)

        // Reads local saved Slopes
        readSlopes();

        locationManager = LocationManager(this)
        locationManager.checkLocationPermission()

        barometerManager = BarometerManager(this)

        accelerometerManager = AccelerometerManager(this)


        // Set the content of the activity.
        setContent {
            // Creates and remembers a navigation controller for managing app navigation.
            val navController: NavHostController = rememberNavController()

            // Remembers the state of button visibility.
            var buttonsVisible = remember { mutableStateOf(true) }

            // Scaffold is a layout that implements the basic material design visual layout structure.
            Scaffold(
                // Defines the bottom bar of the Scaffold.
                bottomBar = {
                    // Calls BottomBar composable function, passing the navController and button visibility state.
                    BottomBar(
                        navController = navController,
                        state = buttonsVisible,
                        modifier = Modifier
                    )
                }
            ){
                // Sets up the navigation graph for the app.
                NavigationGraph(navController = navController)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()

        locationManager.stopLocationUpdates()
        barometerManager.stop()
    }

    private fun readSlopes(){

        slopesArray = dbHelper.getAllSlopes().toMutableList()

    }
}



