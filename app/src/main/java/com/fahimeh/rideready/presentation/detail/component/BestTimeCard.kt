package com.fahimeh.rideready.presentation.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Empfehlungskarte: Bestes Zeitfenster + Grund + Score.
 */
@Composable
fun BestTimeCard(
    timeRange: String,
    scoreText: String,
    reason: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Recommended time",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = timeRange,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = scoreText,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = reason,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}