package com.github.ahmednmahran.elmtickettracking.features.tracking.data.model

import java.util.*

/**
 * Represents worker tracking data including location and device information
 * @property id Unique identifier for the tracking record
 * @property workerId ID of the worker being tracked
 * @property busNumber Identifier for the vehicle/unit
 * @property latitude GPS latitude coordinate
 * @property longitude GPS longitude coordinate
 * @property timestamp When the tracking data was recorded
 * @property speed Current speed in km/h (optional)
 * @property batteryLevel Device battery percentage (optional)
 */
data class TrackingData(
    val id: String,
    val workerId: String,
    val busNumber: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Date = Date(),
    val speed: Float? = null,
    val batteryLevel: Int? = null
) {
    companion object {
        /**
         * Helper extension for formatted time display
         */
        val TrackingData.formattedTime: String
            get() = java.text.SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(timestamp)
        
        /**
         * Helper extension for coordinates display
         */
        val TrackingData.formattedCoordinates: String
            get() = "%.6f, %.6f".format(latitude, longitude)
    }
}