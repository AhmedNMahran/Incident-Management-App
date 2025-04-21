package com.github.ahmednmahran.elmtickettracking.features.create.data.model

import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.core.data.model.IncidentType
import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.features.create.domain.CreateIncidentRepository
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import javax.inject.Inject

class CreateIncidentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CreateIncidentRepository {
    override suspend fun submitIncident(incident: CreateIncidentRequest): Result<List<Incident>> {
        return try {
            val response = apiService.submitIncident(incident)
            if (response.isSuccessful) {
                Result.Success(response.body()?.incidents ?: emptyList())
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getIncidentTypes(): Result<List<IncidentType>> {
        return try {
            val response = apiService.getIncidentTypes()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}