package com.github.ahmednmahran.elmtickettracking.features.auth.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ahmednmahran.elmtickettracking.features.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun login(email: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.login(email)) {
                is Result.Success -> {
                    _events.send(LoginEvent.Success)
                }
                is Result.Error -> {
                    _events.send(LoginEvent.Error(result.exception.message ?: "Login failed"))
                }

                Result.Loading -> {}
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}

data class LoginState(
    val isLoading: Boolean = false
)

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}