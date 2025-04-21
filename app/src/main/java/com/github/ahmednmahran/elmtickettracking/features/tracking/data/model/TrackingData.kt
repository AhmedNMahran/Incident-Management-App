package com.github.ahmednmahran.elmtickettracking.features.tracking.data.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class TrackingData(
    @SerializedName("TrackingLongitude")
    val longitude: Double,
    @SerializedName("TrackingLatitude")
    val latitude: Double,
    @SerializedName("TrackingTime")
    val trackingTime: String,
    @SerializedName("AssigedWorkerId")
    val assignedWorkerId: String,
    @SerializedName("TrackingId")
    val trackingId: String
) {
    companion object {
        /**
         * Helper extension for formatted time display
         */
        fun TrackingData.formattedTime(): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return try {
                val date = inputFormat.parse(trackingTime)
                if (date != null) {
                    outputFormat.format(date)
                } else {
                    "Invalid Date"
                }
            } catch (e: Exception) {
                "Invalid Date"
            }
        }

        /**
         * Helper extension for coordinates display
         */
        fun TrackingData.formattedCoordinates(): String {
            return "%.6f, %.6f".format(latitude, longitude)
        }
    }
}