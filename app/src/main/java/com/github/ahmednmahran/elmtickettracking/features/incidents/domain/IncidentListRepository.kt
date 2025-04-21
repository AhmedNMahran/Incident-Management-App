package com.github.ahmednmahran.elmtickettracking.features.incidents.domain


import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import com.github.ahmednmahran.elmtickettracking.core.common.Result

interface IncidentListRepository {
    suspend fun getIncidents(startDate: String? = null): Result<List<Incident>>
    suspend fun filterIncidentsByStatus(status: Int): Result<List<Incident>>
    suspend fun searchIncidents(query: String): Result<List<Incident>>
    suspend fun filterIncidentsByDateRange(startDate: String, endDate: String): Result<List<Incident>>
}