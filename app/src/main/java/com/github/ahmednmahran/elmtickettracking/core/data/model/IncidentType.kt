package com.github.ahmednmahran.elmtickettracking.core.data.model

data class IncidentType(
    val id: Int,
    val arabicName: String,
    val englishName: String,
    val subTypes: List<IncidentSubType> = emptyList()
)

data class IncidentSubType(
    val id: Int,
    val arabicName: String,
    val englishName: String,
    val categoryId: Int
) {
    // Helper property for display
    val displayName: String
        get() = "$englishName ($arabicName)"
}