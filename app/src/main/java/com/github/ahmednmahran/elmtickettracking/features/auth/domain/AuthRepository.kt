package com.github.ahmednmahran.elmtickettracking.features.auth.domain

import com.github.ahmednmahran.elmtickettracking.core.common.Result

interface AuthRepository {
    suspend fun login(email: String): Result<Unit>
    suspend fun verifyOtp(email: String, otp: String): Result<String>
    suspend fun logout()
}