package com.github.ahmednmahran.elmtickettracking.features.tracking.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.tracking.domain.TrackingRepository
import com.github.ahmednmahran.elmtickettracking.features.tracking.data.model.TrackingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val repository: TrackingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TrackingState())
    val state = _state.asStateFlow()

    init {
        getTrackingData()
    }

    private fun getTrackingData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.getTrackingData()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(isLoading = false, stats = result.data)
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.exception.message)
                    }
                }
                Result.Loading -> {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }
}

data class TrackingState(
    val stats: List<TrackingData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)