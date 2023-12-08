package com.example.android_project_3133142

class Altitude(val max: Int, val min: Int, val delta: Int)

class Slope(
    val id: Long?, // ID ist jetzt eine optionale Long
    val maxVelocity: Int,
    val avgVelocity: Int,
    val distance: Int,
    val altitude: Altitude,
    val runs: Int,
    val duration: String,
    val gradient: Int,
    val location: String,
    val date: String
) {
    override fun toString(): String {
        return "Slope(id=$id, maxVelocity=$maxVelocity, avgVelocity=$avgVelocity, distance=$distance, " +
                "altitude=${altitude.max}/${altitude.min}/${altitude.delta}, runs=$runs, " +
                "duration=$duration, gradient=$gradient)"
    }
}
