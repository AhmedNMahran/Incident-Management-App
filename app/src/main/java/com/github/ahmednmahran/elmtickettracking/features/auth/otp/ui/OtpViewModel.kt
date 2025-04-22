package com.github.ahmednmahran.elmtickettracking.features.auth.otp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.auth.domain.AuthRepository
import com.github.ahmednmahran.elmtickettracking.features.auth.otp.ui.model.OtpEvent
import com.github.ahmednmahran.elmtickettracking.features.auth.otp.ui.model.OtpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [OtpViewModel]
 *
 * A ViewModel responsible for managing the UI state and business logic related to OTP (One-Time Password) verification.
 *
 * This ViewModel interacts with the [AuthRepository] to handle OTP verification and exposes the current state
 * and events through [StateFlow] and [SharedFlow] respectively.
 *
 * @property repository The [AuthRepository] used to perform OTP verification.
 */
@HiltViewModel
class OtpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // UI State
    private val _state = MutableStateFlow(OtpState())
    val state: StateFlow<OtpState> = _state.asStateFlow()

    // Events
    private val _events = MutableSharedFlow<OtpEvent>()
    val events = _events.asSharedFlow()

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            when (val result = repository.verifyOtp(email, otp)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isVerified = true
                    )
                    _events.emit(OtpEvent.VerificationSuccess(result.data))
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                    _events.emit(OtpEvent.VerificationError(result.exception.message ?: "Verification failed"))
                }
                else -> {}
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}