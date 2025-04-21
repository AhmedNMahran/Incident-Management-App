package com.github.ahmednmahran.elmtickettracking.features.dashboard.data

import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.StatusCount
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.DashboardResponse
import com.github.ahmednmahran.elmtickettracking.features.dashboard.domain.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DashboardRepository {
    override suspend fun getDashboardStats(): Result<List<StatusCount>> {
        return try {
            val response = apiService.getDashboardStats()
            if (response.isSuccessful) {
                Result.Success(response.body()?.mapToStatusCounts()?: emptyList())
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

fun DashboardResponse.mapToStatusCounts(): List<StatusCount> {
    return incidents.map { incident ->
        StatusCount(
            status = incident.status,
            count = incident._count.status
        )
    }
}