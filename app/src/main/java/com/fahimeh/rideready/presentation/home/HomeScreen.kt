package com.fahimeh.rideready.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.core.extension.formatTemperature
import com.fahimeh.rideready.core.extension.toDateLabel
import com.fahimeh.rideready.core.extension.toDayLabel
import com.fahimeh.rideready.presentation.home.component.BestDayCard
import com.fahimeh.rideready.presentation.home.component.ForecastDayCard

/**
 * Startbildschirm der App.
 *
 * Rendert die Oberfläche basierend auf dem HomeUiState.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (String) -> Unit, //date
    onNavigateToCities: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()
    val state by viewModel.uiState.collectAsState()

    when (val uiState = state) {

        HomeUiState.Loading -> {
            Text("Loading...")
        }

        HomeUiState.Empty -> {
            Text("No forecast data available")
        }

        is HomeUiState.Error -> {
            Column {
                Text("Error: ${uiState.message}")
                Button(onClick = { viewModel.refresh() }) {
                    Text("Retry")
                }
            }
        }

        is HomeUiState.Success -> {
            Column {
                Text(
                    text = "Forecast for ${uiState.selectedCityName}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Zeigt die berechnete Empfehlung an, falls vorhanden.
                uiState.bestDay?.let { best ->
                    BestDayCard(
                        dayLabel = "${best.date.toDayLabel()} • ${best.date.toDateLabel()}",
                        score = uiState.bestScore?.score ?: 0,
                        reason = uiState.bestScore?.reason ?: ""
                    )
                }

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(uiState.days) { day ->
                        ForecastDayCard(
                            dayLabel = "${day.date.toDayLabel()} • ${day.date.toDateLabel()}",
                            temperature = "${formatTemperature(day.minTempC, settings.temperatureUnit)} / ${formatTemperature(day.maxTempC, settings.temperatureUnit)}",
                            onClick = {
                                onNavigateToDetail(day.date.toString())
                            }
                        )
                    }
                }
            }
        }
    }
}