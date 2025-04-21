package com.github.ahmednmahran.elmtickettracking.features.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.ahmednmahran.elmtickettracking.core.common.ErrorMessage
import com.github.ahmednmahran.elmtickettracking.core.common.FullScreenLoading
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.StatusCount
import com.github.ahmednmahran.elmtickettracking.features.dashboard.ui.components.DashboardButton
import com.github.ahmednmahran.elmtickettracking.features.dashboard.ui.components.PieChart
import com.github.ahmednmahran.elmtickettracking.features.dashboard.ui.components.StatusLegend

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onIncidentsClick: () -> Unit,
    onTrackingClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dashboard") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when {
                state.isLoading -> FullScreenLoading()
                state.error != null -> ErrorMessage(state.error)
                else -> DashboardContent(
                    stats = state.stats,
                    onIncidentsClick = onIncidentsClick,
                    onTrackingClick = onTrackingClick
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    stats: List<StatusCount>,
    onIncidentsClick: () -> Unit,
    onTrackingClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Incident Status Overview",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            PieChart(
                data = stats,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            StatusLegend(data = stats)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DashboardButton(
                    icon = Icons.AutoMirrored.Filled.List,
                    text = "View Incidents",
                    onClick = onIncidentsClick
                )

                DashboardButton(
                    icon = Icons.Default.LocationOn,
                    text = "Worker Tracking",
                    onClick = onTrackingClick
                )
            }
        }
    }
}