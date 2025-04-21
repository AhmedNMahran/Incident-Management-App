package com.github.ahmednmahran.elmtickettracking.features.create.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.create.data.model.CreateIncidentRequest
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import com.github.ahmednmahran.elmtickettracking.features.create.domain.CreateIncidentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateIncidentViewModel @Inject constructor(
    private val repository: CreateIncidentRepository
) : ViewModel() {

    private val _creationResult = MutableStateFlow<Result<List<Incident>>?>(null)
    val creationResult: StateFlow<Result<List<Incident>>?> = _creationResult

    fun submitIncident(request: CreateIncidentRequest) {
        viewModelScope.launch {
            val result = repository.submitIncident(request)
            _creationResult.value = result
        }
    }
}