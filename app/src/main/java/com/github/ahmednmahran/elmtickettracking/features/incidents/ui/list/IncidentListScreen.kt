package com.github.ahmednmahran.elmtickettracking.features.incidents.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.ahmednmahran.elmtickettracking.core.common.ErrorMessage
import com.github.ahmednmahran.elmtickettracking.core.common.FullScreenLoading
import com.github.ahmednmahran.elmtickettracking.core.common.formatDate
import com.github.ahmednmahran.elmtickettracking.features.create.ui.components.CreateIncidentDialog
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentListScreen(
    viewModel: IncidentListViewModel = hiltViewModel(),
    onIncidentClick: (String) -> Unit,
    onFilterClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showFilterDialog by remember { mutableStateOf(false) }
    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf<Int?>(null) }
    var selectedDate by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(selectedStatus, selectedDate) {
        viewModel.loadIncidents(selectedDate)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incidents") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Filter")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Incident")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                state.isLoading -> FullScreenLoading()
                state.error != null -> ErrorMessage(state.error)
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.incidents.filter {
                            selectedStatus == null || it.status == selectedStatus
                        }, key = { it.id }) { incident ->
                            IncidentListItem(
                                incident = incident,
                                onClick = { onIncidentClick(incident.id) }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }

    if (showFilterDialog) {
        AlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = { Text("Filter Incidents") },
            text = {
                Column {
                    Text("Status", style = MaterialTheme.typography.labelLarge)
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        FilterChip(
                            selected = selectedStatus == 0,
                            onClick = { selectedStatus = if (selectedStatus == 0) null else 0 },
                            label = { Text("Submitted") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = selectedStatus == 1,
                            onClick = { selectedStatus = if (selectedStatus == 1) null else 1 },
                            label = { Text("In Progress") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = selectedStatus == 2,
                            onClick = { selectedStatus = if (selectedStatus == 2) null else 2 },
                            label = { Text("Completed") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = selectedStatus == 3,
                            onClick = { selectedStatus = if (selectedStatus == 3) null else 3 },
                            label = { Text("Rejected") }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Date", style = MaterialTheme.typography.labelLarge)
                    val datePickerState = rememberDatePickerState()
                    var showDatePicker by remember { mutableStateOf(false) }

                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedDate ?: "Select date")
                    }

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        selectedDate = datePickerState.selectedDateMillis?.let {
                                            Instant.ofEpochMilli(it)
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                                .toString()
                                        }
                                        showDatePicker = false
                                    }
                                ) { Text("OK") }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        selectedDate = null
                                        showDatePicker = false
                                    }
                                ) { Text("Clear") }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showFilterDialog = false }
                ) { Text("Apply") }
            }
        )
    }
    if (showCreateDialog) {
        CreateIncidentDialog(
            issuerId = "user123", // replace with actual user ID from session/store
            latitude = 24.7136,
            longitude = 46.6753,
            onDismiss = {
                showCreateDialog = false
                viewModel.loadIncidents(selectedDate)
            }
        )
    }
}

@Composable
fun IncidentListItem(
    incident: Incident,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "#${incident.id.take(8)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                StatusChip(status = incident.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = incident.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Created: ${incident.createdAt.formatDate()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            if (incident.medias?.isNotEmpty() == true) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${incident.medias.size} attachment(s)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: Int) {
    val (text, color) = when (status) {
        0 -> "Submitted" to Color(0xFFFF5722)
        1 -> "In Progress" to Color(0xFF2196F3)
        2 -> "Completed" to Color(0xFF4CAF50)
        3 -> "Rejected" to Color(0xFFF44336)
        else -> "Unknown" to Color.Gray
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall
        )
    }
}