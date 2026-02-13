package com.fahimeh.rideready.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.R

/**
 * Zeigt den empfohlenen Tag für eine Outdoor-Aktivität.
 *
 * Aktuell mit Dummy-Daten umgesetzt.
 */
@Composable
fun BestDayCard(
    dayLabel: String,
    score: Int
) {
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_title_best_day),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = dayLabel,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.home_score_label, score),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BestDayCardPreview() {
    BestDayCard(
        dayLabel = "Saturday",
        score = 82
    )
}