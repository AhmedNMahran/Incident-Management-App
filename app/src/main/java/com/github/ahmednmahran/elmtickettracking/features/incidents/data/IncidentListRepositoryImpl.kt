package com.github.ahmednmahran.elmtickettracking.features.incidents.data

import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.incidents.domain.IncidentListRepository
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.*

class IncidentListRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : IncidentListRepository {

    // Cache for local filtering
    private var cachedIncidents: List<Incident> = emptyList()

    override suspend fun getIncidents(startDate: String?): Result<List<Incident>> {
        return try {
            val response = apiService.getIncidents(startDate)
            if (response.isSuccessful) {
                cachedIncidents = response.body()?.incidents ?: emptyList()
                Result.Success(cachedIncidents)
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun filterIncidentsByStatus(status: Int): Result<List<Incident>> {
        return try {
            val filtered = cachedIncidents.filter { it.status == status }
            Result.Success(filtered)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun searchIncidents(query: String): Result<List<Incident>> {
        return try {
            val results = cachedIncidents.filter { incident ->
                incident.description.contains(query, ignoreCase = true) ||
                        incident.id.contains(query, ignoreCase = true)
            }
            Result.Success(results)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun filterIncidentsByDateRange(
        startDate: String,
        endDate: String
    ): Result<List<Incident>> {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val start = dateFormat.parse(startDate)
            val end = dateFormat.parse(endDate)

            val filtered = cachedIncidents.filter { incident ->
                val incidentDate = dateFormat.parse(incident.createdAt.substring(0, 10))
                incidentDate in start..end
            }

            Result.Success(filtered)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}