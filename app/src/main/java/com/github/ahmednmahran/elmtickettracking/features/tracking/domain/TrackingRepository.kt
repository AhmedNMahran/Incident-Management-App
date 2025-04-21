package com.github.ahmednmahran.elmtickettracking.features.tracking.domain

import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.tracking.data.model.TrackingData

interface TrackingRepository {
    suspend fun trackWorker(trackingData: List<TrackingData>): Result<List<TrackingData>>

}