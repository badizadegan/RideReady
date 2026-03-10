package com.fahimeh.rideready.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        HomeUiState.Empty -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "No forecast data available",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Please add or select a city to see the forecast.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        is HomeUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Something went wrong",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = { viewModel.refresh() }) {
                        Text("Retry")
                    }
                }
            }
        }

        is HomeUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Forecast for ${uiState.selectedCityName}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Best day and upcoming weather overview",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Zeigt die berechnete Empfehlung an, falls vorhanden.
                uiState.bestDay?.let { best ->
                    item {
                        BestDayCard(
                            dayLabel = "${best.date.toDayLabel()} • ${best.date.toDateLabel()}",
                            score = uiState.bestScore?.score ?: 0,
                            reason = uiState.bestScore?.reason ?: ""
                        )
                    }
                }

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