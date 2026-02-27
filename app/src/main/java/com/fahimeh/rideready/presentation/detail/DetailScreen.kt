package com.fahimeh.rideready.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.presentation.detail.component.BestTimeCard
import com.fahimeh.rideready.presentation.detail.component.DetailSummaryCard
import com.fahimeh.rideready.presentation.detail.component.HourRow

/**
 * Detailansicht für einen ausgewählten Tag.
 *
 * zeigt Tagesübersicht und stündliche Werte.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    date: String,
    viewModel: DetailViewModel
) {
    val day = viewModel.getDay(date)
    val bestWindow = viewModel.getBestWindow(date)

    if (day == null) {
        Text("No data")
        return
    }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Summary Card
        item {
            DetailSummaryCard(
                dateLabel = "Detail for: ${day.date}",
                minTemp = "${day.minTempC}°C",
                maxTemp = "${day.maxTempC}°C",
                rain = "${day.precipitationMm} mm",
                wind = "${day.windSpeedKmh} km/h"
            )
        }

        // Recommended Time Card
        bestWindow?.let { (window, result) ->
            item {
                BestTimeCard(
                    timeRange = "${window.start.toLocalTime()} - ${window.end.toLocalTime()}",
                    scoreText = "Score: ${result.score}",
                    reason = result.reason
                )
            }
        } ?: run {
            item { Text(text = "No recommended time window") }
        }

        // Hourly rows (sorted list)
        items(day.hourly) { h ->
            HourRow(
                time = h.time.toLocalTime().toString(),
                temp = "${h.temperatureC}°C",
                rain = "${h.precipitationMm}mm",
                wind = "${h.windSpeedKmh}km/h"
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}