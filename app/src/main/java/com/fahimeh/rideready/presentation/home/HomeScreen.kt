package com.fahimeh.rideready.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
    onNavigateToDetail: () -> Unit,
    onNavigateToCities: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column {
        BestDayCard(
            dayLabel = "Saturday",
            score = 82
        )

        LazyColumn {
            items(5) { index ->
                ForecastDayCard(
                    dayLabel = "Day ${index + 1}",
                    temperature = "18°C"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        onNavigateToDetail = {},
        onNavigateToCities = {},
        onNavigateToSettings = {}
    )
}