package com.example.android_project_3133142.manager

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android_project_3133142.distance
import com.example.android_project_3133142.latitude
import com.example.android_project_3133142.longitude
import com.example.android_project_3133142.timestamp
import com.example.android_project_3133142.objects.calculateDistance
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*

var lastLatitude = 0.0
var lastLongitude = 0.0

class LocationManager(private val activity: Activity) {
    // Initialize the FusedLocationProviderClient for interacting with the location services.
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    // Define a constant for the location permission request code.
    private val locationPermissionRequestCode = 1

    // Configure the location request parameters.
    private val locationRequest = LocationRequest.Builder(10000L) // Set the interval for location updates (10 seconds).
        .setMinUpdateIntervalMillis(5000L) // Optionally, set the fastest interval for location updates (5 seconds).
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY) // Set the priority to high accuracy.
        .build()

    // Define a callback for handling location updates.
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            
            for (location in p0.locations) {
                // Process each location in the result.
                println("Lat: " + location.latitude + " Long: " + location.longitude + " Alt: " + location.altitude + " Zeit: " + SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()))
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val weatherService = WeatherService()
                weatherService.getWeather(location.latitude, location.longitude)

                // Distance Calculation
                if (lastLongitude != 0.0 && lastLatitude != 0.0){
                    val distanceTemp = calculateDistance(lastLatitude, lastLongitude, location.latitude, location.longitude)
                    distance = round(distanceTemp).toInt().toString()
                }else{
                    // If there is no last Location
                    lastLongitude = location.longitude
                    lastLatitude = location.latitude
                }
            }
        }
    }

    // Check if location permissions are granted; if not, request them.
    fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionRequestCode
            )
        } else {
            startLocationUpdates()
        }
    }

    // Handle the result of the location permission request.
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            locationPermissionRequestCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startLocationUpdates()
                } else {
                    println("Authorization was denied") // Permission was denied
                }
            }
        }
    }

    // Start receiving location updates if permission is granted.
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    // Stop receiving location updates.
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
