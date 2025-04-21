package com.github.ahmednmahran.elmtickettracking.features.auth.data

import com.github.ahmednmahran.elmtickettracking.core.data.local.SharedPrefsManager
import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.auth.domain.AuthRepository

import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: SharedPrefsManager
) : AuthRepository {
    override suspend fun login(email: String): Result<Unit> {
        return try {
            val response = apiService.login(mapOf("email" to email))
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): Result<String> {
        return try {
            val response = apiService.verifyOtp(mapOf(
                "email" to email,
                "otp" to otp
            ))
            if (response.isSuccessful) {
                val token = response.body()?.token ?: ""
                prefs.saveToken(token)
                Result.Success(token)
            } else {
                Result.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun logout() {
        // Perform any necessary logout actions
    }
}