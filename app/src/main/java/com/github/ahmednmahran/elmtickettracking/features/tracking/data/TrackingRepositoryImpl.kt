package com.github.ahmednmahran.elmtickettracking.features.tracking.data

import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.tracking.data.model.TrackingData
import com.github.ahmednmahran.elmtickettracking.features.tracking.domain.TrackingRepository
import javax.inject.Inject

class TrackingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TrackingRepository {
    override suspend fun trackWorker(trackingData: List<TrackingData>): Result<List<TrackingData>> {
        return try {
            val response = apiService.trackWorker(trackingData)
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