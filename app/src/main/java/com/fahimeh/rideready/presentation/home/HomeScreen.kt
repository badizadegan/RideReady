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
 * Aktuell mit Dummy-UI umgesetzt,
 * um die Struktur der Oberfläche zu zeigen.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: () -> Unit,
    onNavigateToCities: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    when (val s = state) {

        HomeUiState.Loading -> {
            Text("Loading...")
        }

        HomeUiState.Empty -> {
            Text("No forecast data available")
        }

        is HomeUiState.Error -> {
            Column {
                Text("Error: ${s.message}")
                Button(onClick = { viewModel.loadForecast() }) {
                    Text("Retry")
                }
            }
        }

        is HomeUiState.Success -> {
            Column {

                // Einfach erstes Element anzeigen (Best Day später)
                val firstDay = s.days.firstOrNull()

                firstDay?.let {
                    BestDayCard(
                        dayLabel = it.date.toString(),
                        score = 0 // Score kommt später
                    )
                }

                LazyColumn {
                    items(s.days) { day ->
                        ForecastDayCard(
                            dayLabel = day.date.toString(),
                            temperature = "${day.maxTempC}°C"
                        )
                    }
                }
            }
        }
    }
}