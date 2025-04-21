package com.github.ahmednmahran.elmtickettracking.features.tracking.data.model

/**
 * Worker information model
 * @property id Unique worker identifier
 * @property name Worker's full name
 * @property phone Contact number
 * @property vehicleAssigned Vehicle/unit number
 */
data class Worker(
    val id: String,
    val name: String,
    val phone: String,
    val vehicleAssigned: String
)