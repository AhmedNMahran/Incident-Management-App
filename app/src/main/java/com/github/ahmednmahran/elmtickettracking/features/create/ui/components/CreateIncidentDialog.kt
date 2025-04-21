package com.github.ahmednmahran.elmtickettracking.features.create.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.ahmednmahran.elmtickettracking.features.create.data.model.CreateIncidentRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIncidentDialog(
    issuerId: String,
    latitude: Double,
    longitude: Double,
    onDismiss: () -> Unit,
    viewModel: CreateIncidentViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var typeId by remember { mutableStateOf("1") } // hardcoded; replace with dropdown if needed

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val request = CreateIncidentRequest(
                    description = description,
                    latitude = latitude,
                    longitude = longitude,
                    typeId = typeId.toIntOrNull() ?: 1,
                    issuerId = issuerId,
                )
                viewModel.submitIncident(request)
                onDismiss()
            }) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("New Incident") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = typeId,
                    onValueChange = { typeId = it },
                    label = { Text("Type ID") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}