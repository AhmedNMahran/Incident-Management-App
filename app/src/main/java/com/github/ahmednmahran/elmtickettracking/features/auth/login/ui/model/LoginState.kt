package com.github.ahmednmahran.elmtickettracking.features.auth.login.ui.model

data class LoginState(
    val isLoading: Boolean = false
)

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}