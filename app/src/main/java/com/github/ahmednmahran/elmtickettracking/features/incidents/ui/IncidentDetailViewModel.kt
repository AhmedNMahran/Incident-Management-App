package com.github.ahmednmahran.elmtickettracking.features.incidents.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.incidents.domain.IncidentDetailRepository
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident

@HiltViewModel
class IncidentDetailViewModel @Inject constructor(
    private val repository: IncidentDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(IncidentDetailState())
    val state: StateFlow<IncidentDetailState> = _state.asStateFlow()

    init {
        val incidentId = savedStateHandle.get<String>("id") ?: ""
        loadIncident(incidentId)
    }

    fun loadIncident(incidentId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            when (val result = repository.getIncident(incidentId)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        incident = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.exception.message ?: "Failed to load incident",
                        isLoading = false
                    )
                }

                Result.Loading -> _state.value = _state.value.copy(isLoading = true)
            }
        }
    }

    fun updateIncident(newStatus: Int) {
        _state.value.incident?.let { incident ->
            viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true)
                
                when (val result = repository.updateIncidentStatus(incident.id, newStatus)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            incident = result.data.copy(status = newStatus),
                            isLoading = false,
                            error = null
                        )
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            error = result.exception.message ?: "Failed to update status",
                            isLoading = false
                        )
                    }
                    Result.Loading -> _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }
}

data class IncidentDetailState(
    val incident: Incident? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)