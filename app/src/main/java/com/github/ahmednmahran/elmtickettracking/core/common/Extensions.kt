package com.github.ahmednmahran.elmtickettracking.core.common

import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun Incident.getStatusText(): String {
    return when (status) {
        0 -> "Submitted"
        1 -> "In Progress"
        2 -> "Completed"
        3 -> "Rejected"
        else -> "Unknown"
    }
}