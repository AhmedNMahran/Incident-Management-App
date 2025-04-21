package com.github.ahmednmahran.elmtickettracking.features.incidents.domain

import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident

interface IncidentDetailRepository {
    suspend fun getIncident(id: String): Result<Incident>
    suspend fun updateIncidentStatus(id: String, newStatus: Int): Result<Incident>
    suspend fun addComment(incidentId: String, comment: String): Result<Incident>
}