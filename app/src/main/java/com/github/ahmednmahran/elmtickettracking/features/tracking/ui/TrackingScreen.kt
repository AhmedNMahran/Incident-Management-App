package com.github.ahmednmahran.elmtickettracking.features.tracking.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Worker Tracking") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
//            GoogleMap(
//                modifier = Modifier.fillMaxSize(),
//                cameraPositionState = rememberCameraPositionState {
//                    position = CameraPosition.fromLatLngZoom(
//                        LatLng(24.7136, 46.6753), // Default to Riyadh
//                        12f
//                    )
//                }
//            ) {
//                // Add markers for tracking data
//                state.stats.forEach { data ->
//                    Marker(
//                        state = MarkerState(position = LatLng(data.latitude, data.longitude)),
//                        title = "Worker at ${data.time}",
//                        snippet = "Bus: ${data.busNumber}"
//                    )
//                }
//            }

            if (state.stats.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tracking data available")
                }
            }
        }
    }
}