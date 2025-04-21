package com.github.ahmednmahran.elmtickettracking.features.create.domain

import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.core.data.model.IncidentType
import com.github.ahmednmahran.elmtickettracking.features.create.data.model.CreateIncidentRequest

interface CreateIncidentRepository {
    suspend fun submitIncident(incident: CreateIncidentRequest): Result<List<Incident>>
    suspend fun getIncidentTypes(): Result<List<IncidentType>>
}