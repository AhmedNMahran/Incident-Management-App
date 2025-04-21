package com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model

import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident

data class IncidentResponse(
    val incidents: List<Incident>
)