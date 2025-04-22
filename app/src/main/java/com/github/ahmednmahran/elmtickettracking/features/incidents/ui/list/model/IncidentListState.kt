package com.github.ahmednmahran.elmtickettracking.features.incidents.ui.list.model

import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident

data class IncidentListState(
    val incidents: List<Incident> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)