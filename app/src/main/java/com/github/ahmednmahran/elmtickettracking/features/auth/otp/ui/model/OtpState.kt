package com.github.ahmednmahran.elmtickettracking.features.auth.otp.ui.model

// UI State
data class OtpState(
    val isLoading: Boolean = false,
    val isVerified: Boolean = false,
    val error: String? = null
)

// Events
sealed class OtpEvent {
    data class VerificationSuccess(val token: String) : OtpEvent()
    data class VerificationError(val message: String) : OtpEvent()
}