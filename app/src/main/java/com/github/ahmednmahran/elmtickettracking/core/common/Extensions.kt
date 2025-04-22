package com.github.ahmednmahran.elmtickettracking.core.common

import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Formats a date string from ISO 8601 format to a more user-friendly format.
 *
 * This function extends the String class, allowing you to call it directly on a string
 * representing a date in the ISO 8601 format (yyyy-MM-dd'T'HH:mm:ss.SSS'Z'). It attempts to parse the
 * input string and convert it to the format "MMM dd, yyyy hh:mm a" (e.g., "Jan 01, 2023 02:30 PM").
 *
 * If the input string is not in the expected ISO 8601 format, or if any error occurs during parsing or formatting,
 * the function will return the original input string unchanged.
 *
 * @receiver String The input string representing a date in ISO 8601 format.
 * @return String The formatted date string in the "MMM dd, yyyy hh:mm a" format, or the original string if an error occurred.
 *
 * Example Usage:
 * ```
 * val isoDate = "2023-10-27T10:00:00.000Z"
 * val formattedDate = isoDate.formatDate() // formattedDate will be "Oct 27, 2023 10:00 AM"
 *
 * val invalidDate = "Not a date"
 * val result = invalidDate.formatDate() // result will be "Not a date"
 * ```
 */
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

/**
 * Returns a human-readable string representation of the incident's status.
 *
 * The status is determined by the `status` property of the [Incident] object, which is an integer.
 * This function maps the integer status code to a corresponding text description.
 *
 * Status codes and their meanings:
 *  - 0: "Submitted" - The incident has been submitted.
 *  - 1: "In Progress" - The incident is currently being worked on.
 *  - 2: "Completed" - The incident has been resolved.
 *  - 3: "Rejected" - The incident has been rejected.
 *  - Any other value: "Unknown" - The status code is not recognized.
 *
 * @return A String representing the current status of the incident.
 *         Possible return values: "Submitted", "In Progress", "Completed", "Rejected", "Unknown".
 */
fun Incident.getStatusText(): String {
    return when (status) {
        0 -> "Submitted"
        1 -> "In Progress"
        2 -> "Completed"
        3 -> "Rejected"
        else -> "Unknown"
    }
}