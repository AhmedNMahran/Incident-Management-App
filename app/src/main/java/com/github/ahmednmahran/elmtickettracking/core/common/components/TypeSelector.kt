package com.github.ahmednmahran.elmtickettracking.core.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.ahmednmahran.elmtickettracking.core.data.model.IncidentType

@Composable
fun TypeSelector(
    types: List<IncidentType>,
    selectedType: IncidentType?,
    onTypeSelected: (IncidentType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
//        OutlinedTextField(
//            value = selectedType?.englishName ?: "Select incident type",
//            onValueChange = {},
//            readOnly = true,
//            label = { Text(text = "Incident Type") },
//            modifier = Modifier.fillMaxWidth(),
//            trailingIcon = {
//                DropdownMenuIcon(expanded = expanded)
//            },
//            onClick = { expanded = true }
//        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.englishName) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DropdownMenuIcon(expanded: Boolean) {
    Icon(
        imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
        contentDescription = null
    )
}