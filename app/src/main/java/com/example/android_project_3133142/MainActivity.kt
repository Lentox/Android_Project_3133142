package com.example.android_project_3133142

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.android_project_3133142.controller.reCalcStatistic
import com.example.android_project_3133142.manager.AccelerometerManager
import com.example.android_project_3133142.manager.BarometerManager
import com.example.android_project_3133142.manager.LocationManager
import com.example.android_project_3133142.manager.MyDatabaseHelper
import com.example.android_project_3133142.navigation.BottomBar
import com.example.android_project_3133142.navigation.NavigationGraph

// MainActivity class which extends ComponentActivity, the base class for activities in Jetpack Compose.
class MainActivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager
    private  lateinit var barometerManager: BarometerManager
    private lateinit var accelerometerManager: AccelerometerManager

    private lateinit var dbHelper: MyDatabaseHelper
    // Suppresses Lint warnings for the unused Scaffold padding parameter.
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    // Marks that this code uses experimental features of Material3.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = MyDatabaseHelper(this)

        // Reads local saved Slopes
        readSlopes()

        locationManager = LocationManager(this)
        locationManager.checkLocationPermission()

        barometerManager = BarometerManager(this)

        accelerometerManager = AccelerometerManager(this)


        // Set the content of the activity.
        setContent {
            // Creates and remembers a navigation controller for managing app navigation.
            val navController: NavHostController = rememberNavController()

            // Scaffold is a layout that implements the basic material design visual layout structure.
            Scaffold(
                // Defines the bottom bar of the Scaffold.
                bottomBar = {
                    // Calls BottomBar composable function, passing the navController and button visibility state.
                    BottomBar(
                        navController = navController,
                    )
                }
            ){
                // Sets up the navigation graph for the app.
                NavigationGraph(navController = navController)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()

        locationManager.stopLocationUpdates()
        barometerManager.stop()
    }

    private fun readSlopes(){

        slopesArray = dbHelper.getAllSlopes().toMutableList()
        reCalcStatistic()

    }
}



