package com.github.ahmednmahran.elmtickettracking.features.incidents.data.model

data class Incident(
    val id: String,
    val description: String,
    val status: Int,
    val priority: Int?,
    val typeId: Int,
    val latitude: Double,
    val longitude: Double,
    val issuerId: String,
    val assigneeId: String?,
    val createdAt: String,
    val updatedAt: String,
    val medias: List<Media>? = emptyList()
)

data class Media(
    val id: String,
    val mimeType: String,
    val url: String,
    val type: Int,
    val incidentId: String
)