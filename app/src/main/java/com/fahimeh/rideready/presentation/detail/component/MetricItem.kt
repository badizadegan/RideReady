package com.fahimeh.rideready.presentation.detail.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Kleines UI-Element für Metriken wie Rain/Wind/Temp.
 */
@Composable
fun MetricItem(
    label: String,
    value: String
) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyMedium
    )
}