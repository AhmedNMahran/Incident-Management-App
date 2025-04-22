package com.github.ahmednmahran.elmtickettracking.features.incidents.ui.details.model

import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident

data class IncidentDetailState(
    val incident: Incident? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)