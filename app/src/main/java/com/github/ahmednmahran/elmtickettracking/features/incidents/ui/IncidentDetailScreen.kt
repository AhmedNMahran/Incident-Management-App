package com.github.ahmednmahran.elmtickettracking.features.incidents.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.github.ahmednmahran.elmtickettracking.core.common.ErrorMessage
import com.github.ahmednmahran.elmtickettracking.core.common.FullScreenLoading
import com.github.ahmednmahran.elmtickettracking.core.common.formatDate
import com.github.ahmednmahran.elmtickettracking.core.common.getStatusText
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentDetailScreen(
    incidentId: String,
    viewModel: IncidentDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }

    LaunchedEffect(incidentId) {
        viewModel.loadIncident(incidentId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incident Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Filled.Edit, "Edit")
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> FullScreenLoading()
            state.error != null -> ErrorMessage(state.error)
            state.incident != null -> {
                IncidentDetailContent(
                    incident = state.incident as Incident,
                    modifier = Modifier.padding(padding)
                )
                if (showEditDialog) {
                    EditIncidentDialog(
                        incident = state.incident as Incident,
                        onDismiss = { showEditDialog = false },
                        onSave = { updatedIncident ->
                            viewModel.updateIncident(updatedIncident.status)
                            showEditDialog = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditIncidentDialog(
    incident: Incident,
    onDismiss: () -> Unit,
    onSave: (Incident) -> Unit
) {
    var selectedStatus by remember { mutableIntStateOf(incident.status) }



    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Incident") },
        text = {
            Column {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    FilterChip(
                        selected = selectedStatus == 0,
                        onClick = { selectedStatus =  0 },
                        label = { Text("Submitted") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(
                        selected = selectedStatus == 1,
                        onClick = { selectedStatus = 1 },
                        label = { Text("In Progress") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(
                        selected = selectedStatus == 2,
                        onClick = { selectedStatus = 2 },
                        label = { Text("Completed") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(
                        selected = selectedStatus == 3,
                        onClick = { selectedStatus = 3 },
                        label = { Text("Rejected") }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedIncident = incident.copy(status = selectedStatus)
                    onSave(updatedIncident)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun IncidentDetailContent(
    incident: Incident,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "#${incident.id.take(8)}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            StatusChip(status = incident.status)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = incident.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (incident.medias?.isNotEmpty() == true) {
            Text(
                text = "Attachments",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow {
                incident.medias?.let {
                    items(incident.medias) { media ->
                        AsyncImage(
                            model = media.url,
                            contentDescription = "Incident image",
                            modifier = Modifier
                                .size(120.dp)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Details",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        IncidentDetailItem("Status", incident.getStatusText())
        IncidentDetailItem("Created", incident.createdAt.formatDate())
        IncidentDetailItem("Updated", incident.updatedAt.formatDate())
    }
}

@Composable
private fun IncidentDetailItem(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}