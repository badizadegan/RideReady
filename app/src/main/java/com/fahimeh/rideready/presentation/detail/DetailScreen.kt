package com.fahimeh.rideready.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Detailansicht für einen ausgewählten Tag.
 *
 * zeigt Tagesübersicht und stündliche Werte.
 */
@Composable
fun DetailScreen(
    date: String,
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    val day = viewModel.getDay(date)

    if (day == null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "No data for selected day")
            Button(onClick = onBack) { Text("Back") }
        }
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Detail for: ${day.date}")
        Text(text = "Min: ${day.minTempC}°C  Max: ${day.maxTempC}°C")
        Text(text = "Rain: ${day.precipitationMm}mm  Wind: ${day.windSpeedKmh}km/h")

        LazyColumn {
            items(day.hourly) { h ->
                Text(
                    text = "${h.time.toLocalTime()}  Temp: ${h.temperatureC}°C  " +
                            "Rain: ${h.precipitationMm}mm  Wind: ${h.windSpeedKmh}km/h"
                )
            }
        }
    }
}