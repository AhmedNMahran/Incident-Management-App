package com.github.ahmednmahran.elmtickettracking.features.incidents.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ahmednmahran.elmtickettracking.features.incidents.domain.IncidentListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class IncidentListViewModel @Inject constructor(
    private val repository: IncidentListRepository
) : ViewModel() {
    private val _state = MutableStateFlow(IncidentListState())
    val state: StateFlow<IncidentListState> = _state

    init {
        loadIncidents()
    }

    fun loadIncidents(startDate: String? = null) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.getIncidents(startDate)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        incidents = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.exception.message,
                        isLoading = false
                    )
                }

                Result.Loading -> _state.value.copy(
                    incidents = emptyList(),
                    error = null,
                    isLoading = true
                )
            }
        }
    }

    fun filterByStatus(status: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.filterIncidentsByStatus(status)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        incidents = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.exception.message,
                        isLoading = false
                    )
                }

                Result.Loading -> _state.value.copy(
                    incidents = emptyList(),
                    error = null,
                    isLoading = true
                )
            }
        }
    }

    fun filterByDateRange(start: String, end: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.filterIncidentsByDateRange(start, end)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        incidents = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.exception.message,
                        isLoading = false
                    )
                }

                Result.Loading -> _state.value.copy(
                    incidents = emptyList(),
                    error = null,
                    isLoading = true
                )
            }
        }
    }
}

data class IncidentListState(
    val incidents: List<Incident> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)