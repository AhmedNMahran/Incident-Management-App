package com.github.ahmednmahran.elmtickettracking.features.dashboard.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.StatusCount

@Composable
fun PieChart(
    data: List<StatusCount>,
    modifier: Modifier = Modifier,
    radius: Dp = 100.dp,
    strokeWidth: Dp = 20.dp
) {
    val colors = listOf(
        Color(0xFFFF5722), // Submitted - Orange
        Color(0xFF2196F3), // In Progress - Blue
        Color(0xFF4CAF50), // Completed - Green
        Color(0xFFF44336)  // Rejected - Red
    )

    val total = data.sumOf { it.count }.toFloat()
    if (total == 0f) return // Don't draw if no data

    Canvas(modifier = modifier.size(radius * 2f)) {
        var startAngle = -90f // Start at top (12 o'clock position)

        data.forEachIndexed { index, item ->
            val sweepAngle = (item.count.toFloat() / total) * 360f
            drawPieSlice(
                color = colors.getOrElse(index) { Color.Gray },
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                strokeWidth = strokeWidth.toPx()
            )
            startAngle += sweepAngle
        }
    }
}

private fun DrawScope.drawPieSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    strokeWidth: Float
) {
    val diameter = size.minDimension
    val radius = diameter / 2f
    val centerOffset = Offset(radius, radius)

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        topLeft = centerOffset - Offset(radius, radius),
        size = Size(diameter, diameter),
        style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
    )
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    val sampleData = listOf(
        StatusCount(0, 20),
        StatusCount(1, 30),
        StatusCount(2, 40),
        StatusCount(3, 10)
    )
    PieChart(data = sampleData)
}

@Preview(showBackground = true)
@Composable
fun EmptyPieChartPreview() {
    PieChart(data = emptyList())
}