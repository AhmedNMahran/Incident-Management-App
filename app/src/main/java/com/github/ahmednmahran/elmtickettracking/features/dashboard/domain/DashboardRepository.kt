package com.github.ahmednmahran.elmtickettracking.features.dashboard.domain

import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.StatusCount

interface DashboardRepository {
    suspend fun getDashboardStats(): Result<List<StatusCount>>
}