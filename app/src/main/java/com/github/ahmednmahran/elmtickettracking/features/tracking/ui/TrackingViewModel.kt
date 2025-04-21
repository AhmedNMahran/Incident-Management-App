package com.github.ahmednmahran.elmtickettracking.features.tracking.ui

import androidx.lifecycle.ViewModel
import com.github.ahmednmahran.elmtickettracking.features.tracking.domain.TrackingRepository
import com.github.ahmednmahran.elmtickettracking.features.tracking.data.model.TrackingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val repository: TrackingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TrackingState())
    val state = _state.asStateFlow()
}

data class TrackingState(
    val stats: List<TrackingData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)