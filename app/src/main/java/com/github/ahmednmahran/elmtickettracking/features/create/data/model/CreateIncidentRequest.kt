package com.github.ahmednmahran.elmtickettracking.features.create.data.model

data class CreateIncidentRequest(
    val description: String,
    val typeId: Int,
    val latitude: Double,
    val longitude: Double,
    val issuerId: String,
)