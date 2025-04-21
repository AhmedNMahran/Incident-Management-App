package com.github.ahmednmahran.elmtickettracking.features.dashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.StatusCount

@Composable
fun StatusLegend(
    data: List<StatusCount>,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFFFF5722), // Submitted
        Color(0xFF2196F3), // In Progress
        Color(0xFF4CAF50), // Completed
        Color(0xFFF44336)  // Rejected
    )

    Column(modifier = modifier) {
        data.forEachIndexed { index, item ->
            LegendItem(
                color = colors.getOrElse(index) { Color.Gray },
                status = item.statusName,
                count = item.count
            )
        }
    }
}

@Composable
private fun LegendItem(
    color: Color,
    status: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(color)
            )
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}