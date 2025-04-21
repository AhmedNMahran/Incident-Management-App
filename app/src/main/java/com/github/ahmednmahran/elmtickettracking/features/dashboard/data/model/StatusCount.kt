package com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model

/**
 * Represents count of incidents by status for dashboard statistics
 * @property status Incident status code (0-3)
 * @property count Number of incidents with this status
 */
data class StatusCount(
    val status: Int,
    val count: Int
) {
    companion object {
        /**
         * Status codes mapping:
         * 0 - Submitted
         * 1 - In Progress
         * 2 - Completed
         * 3 - Rejected
         */
        fun getStatusName(status: Int): String = when (status) {
            0 -> "Submitted"
            1 -> "In Progress"
            2 -> "Completed"
            3 -> "Rejected"
            else -> "Unknown"
        }
    }

    /**
     * Human-readable status name
     */
    val statusName: String
        get() = getStatusName(status)
}