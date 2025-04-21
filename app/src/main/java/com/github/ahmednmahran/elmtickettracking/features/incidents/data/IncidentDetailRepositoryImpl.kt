
package com.github.ahmednmahran.elmtickettracking.features.incidents.data

import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.UpdateIncidentRequest
import com.github.ahmednmahran.elmtickettracking.features.incidents.domain.IncidentDetailRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class IncidentDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : IncidentDetailRepository {

    override suspend fun getIncident(id: String): Result<Incident> {
        return try {
            // Simulate network delay for testing
            delay(500)
            
            // Since mock API doesn't have a direct endpoint for single incident,
            // we fetch all and filter locally
            val response = apiService.getIncidents()
            
            if (response.isSuccessful) {
                val incident = response.body()?.incidents?.find { it.id == id }
                if (incident != null) {
                    Result.Success(incident)
                } else {
                    Result.Error(Exception("Incident not found"))
                }
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateIncidentStatus(id: String, newStatus: Int): Result<Incident> {
        return try {
            // Simulate network delay
            delay(500)
            
            // Mock API will return the updated incident
            val response = apiService.changeIncidentStatus(
                UpdateIncidentRequest(id, newStatus)
            )
            
            if (response.isSuccessful) {
                Result.Success(response.body() ?: throw Exception("Empty response body"))
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun addComment(incidentId: String, comment: String): Result<Incident> {
        return try {
            // Simulate network delay
            delay(500)
            
            // In a real implementation, this would call an API endpoint
            // For mock purposes, we'll return the current incident
            val currentIncident = getIncident(incidentId)
            if (currentIncident is Result.Success) {
                // Simulate updating the description with the new comment
                val updatedIncident = currentIncident.data.copy(
                    description = "${currentIncident.data.description}\n\nComment: $comment"
                )
                Result.Success(updatedIncident)
            } else {
                Result.Error(Exception("Failed to add comment"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}