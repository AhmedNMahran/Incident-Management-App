package com.github.ahmednmahran.elmtickettracking.features.auth.otp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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