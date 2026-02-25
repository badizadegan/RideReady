package com.fahimeh.rideready.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                Button(onClick = { viewModel.loadForecast() }) {
                    Text("Retry")
                }
            }
        }

        is HomeUiState.Success -> {
            Column {

                // Zeigt die berechnete Empfehlung an, falls vorhanden.
                uiState.bestDay?.let { best ->
                    BestDayCard(
                        dayLabel = best.date.toString(),
                        score = uiState.bestScore?.score ?: 0,
                        reason = uiState.bestScore?.reason ?: ""
                    )
                }

                LazyColumn {
                    items(uiState.days) { day ->
                        ForecastDayCard(
                            dayLabel = day.date.toString(),
                            temperature = "${day.maxTempC}°C",
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