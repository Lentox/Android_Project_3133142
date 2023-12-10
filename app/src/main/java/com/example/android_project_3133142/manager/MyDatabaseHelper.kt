package com.example.android_project_3133142.manager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.yml.charts.common.model.Point
import com.example.android_project_3133142.objects.Altitude
import com.example.android_project_3133142.objects.Slope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "database3.db"
        const val DATABASE_VERSION = 3
        const val TABLE_NAME = "slopes"
        const val COLUMN_ID = "id"
        const val COLUMN_MAX_VELOCITY = "max_velocity"
        const val COLUMN_AVG_VELOCITY = "avg_velocity"
        const val COLUMN_DISTANCE = "distance"
        const val COLUMN_ALTITUDE_MAX = "altitude_max"
        const val COLUMN_ALTITUDE_MIN = "altitude_min"
        const val COLUMN_ALTITUDE_DELTA = "altitude_delta"
        const val COLUMN_RUNS = "runs"
        const val COLUMN_DURATION = "duration"
        const val COLUMN_GRADIENT = "gradient"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_DATE = "date"
        const val COLUMN_POINTS = "points"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER,
                $COLUMN_MAX_VELOCITY INTEGER,
                $COLUMN_AVG_VELOCITY INTEGER,
                $COLUMN_DISTANCE INTEGER,
                $COLUMN_ALTITUDE_MAX INTEGER,
                $COLUMN_ALTITUDE_MIN INTEGER,
                $COLUMN_ALTITUDE_DELTA INTEGER,
                $COLUMN_RUNS INTEGER,
                $COLUMN_DURATION TEXT,
                $COLUMN_GRADIENT INTEGER,
                $COLUMN_LOCATION TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_POINTS TEXT
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertSlope(slope: Slope): Long? {
        val db = writableDatabase

        // Convert the list of points into a JSON string
        val pointsDataJson = convertListToJson(slope.pointsData)

        val values = ContentValues().apply {
            put(COLUMN_ID, slope.id)
            put(COLUMN_MAX_VELOCITY, slope.maxVelocity)
            put(COLUMN_AVG_VELOCITY, slope.avgVelocity)
            put(COLUMN_DISTANCE, slope.distance)
            put(COLUMN_ALTITUDE_MAX, slope.altitude.max)
            put(COLUMN_ALTITUDE_MIN, slope.altitude.min)
            put(COLUMN_ALTITUDE_DELTA, slope.altitude.delta)
            put(COLUMN_RUNS, slope.runs)
            put(COLUMN_DURATION, slope.duration)
            put(COLUMN_GRADIENT, slope.gradient)
            put(COLUMN_LOCATION, slope.location)
            put(COLUMN_DATE, slope.date)
            put(COLUMN_POINTS, pointsDataJson)
        }

        return db?.insert(TABLE_NAME, null, values)
    }

    fun deleteSlope(slope: Slope) {
        println("Index tobeDeleted: " + slope.id)
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(slope.id.toString())

        db.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun getAllSlopes(): List<Slope> {
        val db = readableDatabase
        val slopes = mutableListOf<Slope>()

        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_MAX_VELOCITY,
            COLUMN_AVG_VELOCITY,
            COLUMN_DISTANCE,
            COLUMN_ALTITUDE_MAX,
            COLUMN_ALTITUDE_MIN,
            COLUMN_ALTITUDE_DELTA,
            COLUMN_RUNS,
            COLUMN_DURATION,
            COLUMN_GRADIENT,
            COLUMN_LOCATION,
            COLUMN_DATE,
            COLUMN_POINTS
        )

        val cursor: Cursor? = db?.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val slope = Slope(
                    it.getLong(it.getColumnIndexOrThrow(COLUMN_ID)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_MAX_VELOCITY)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_AVG_VELOCITY)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_DISTANCE)),
                    Altitude(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_ALTITUDE_MAX)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_ALTITUDE_MIN)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_ALTITUDE_DELTA))
                    ),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_RUNS)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_DURATION)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_GRADIENT)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_LOCATION)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_DATE)),
                    getSlopePointsData(it.getString(it.getColumnIndexOrThrow(COLUMN_POINTS)))
                )
                slopes.add(slope)
            }
        }

        return slopes
    }

    private fun convertListToJson(pointsData: List<Point>): String {
        return Gson().toJson(pointsData)
    }

    private fun getSlopePointsData(jsonString: String): List<Point> {
        val type = object : TypeToken<List<Point>>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}