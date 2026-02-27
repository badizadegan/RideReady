package com.fahimeh.rideready.presentation.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Eine Zeile der stündlichen Vorhersage.
 */
@Composable
fun HourRow(
    time: String,
    temp: String,
    rain: String,
    wind: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = time, style = MaterialTheme.typography.bodyMedium)
        Text(text = temp, style = MaterialTheme.typography.bodyMedium)
        Text(text = rain, style = MaterialTheme.typography.bodyMedium)
        Text(text = wind, style = MaterialTheme.typography.bodyMedium)
    }
    Divider()
}