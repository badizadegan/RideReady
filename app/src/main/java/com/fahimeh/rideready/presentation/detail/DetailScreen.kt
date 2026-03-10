package com.fahimeh.rideready.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.core.extension.formatTemperature
import com.fahimeh.rideready.core.extension.toDateLabel
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
    val settings by viewModel.settings.collectAsState()
    val day = viewModel.getDay(date)
    val bestWindow = viewModel.getBestWindow(
        dateString = date,
        windowHours = settings.timeWindowHours
    )

    if (day == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No forecast data available for this day",
                style = MaterialTheme.typography.titleMedium
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Summary Card
        item {
            DetailSummaryCard(
                dateLabel = "Detail for: ${day.date.toDateLabel()}",
                minTemp = formatTemperature(day.minTempC, settings.temperatureUnit),
                maxTemp = formatTemperature(day.maxTempC, settings.temperatureUnit),
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
        } ?: item {  Text(
            text = "No recommended time window available",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        }

        item {
            Text(
                text = "Hourly forecast",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Hourly rows (sorted list)
        items(day.hourly) { h ->
            HourRow(
                time = h.time.toLocalTime().toString(),
                temp = formatTemperature(h.temperatureC, settings.temperatureUnit),
                rain = "${h.precipitationMm}mm",
                wind = "${h.windSpeedKmh}km/h"
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}