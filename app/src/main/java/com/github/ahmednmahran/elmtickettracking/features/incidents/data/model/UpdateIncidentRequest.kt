package com.github.ahmednmahran.elmtickettracking.features.incidents.data.model

data class UpdateIncidentRequest(
        val incidentId: String,
        val status: Int)