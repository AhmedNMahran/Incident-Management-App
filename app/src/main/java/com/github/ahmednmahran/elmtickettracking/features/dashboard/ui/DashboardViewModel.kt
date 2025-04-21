package com.github.ahmednmahran.elmtickettracking.features.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.dashboard.domain.DashboardRepository
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.StatusCount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    init {
        loadDashboardStats()
    }

    fun loadDashboardStats() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.getDashboardStats()) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        stats = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.exception.message,
                        isLoading = false
                    )
                }

                Result.Loading -> {}
            }
        }
    }
}

data class DashboardState(
    val stats: List<StatusCount> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)