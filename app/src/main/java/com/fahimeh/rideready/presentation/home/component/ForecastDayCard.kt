package com.fahimeh.rideready.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Stellt die Vorhersage für einen einzelnen Tag dar.
 *
 * Wird später klickbar für die Detailansicht.
 */
@Composable
fun ForecastDayCard(
    dayLabel: String,
    temperature: String
) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = dayLabel)
            Text(text = temperature)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastDayCardPreview() {
    ForecastDayCard(
        dayLabel = "Monday",
        temperature = "17°C"
    )
}