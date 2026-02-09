package com.fahimeh.rideready.presentation.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Detailansicht für einen ausgewählten Tag.
 *
 * Wird später mit Stunden-Vorhersage erweitert.
 */
@Composable
fun DetailScreen(
    onBack: () -> Unit
) {
    Text(text = "Detail Screen")
}

@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    DetailScreen(
        onBack = {}
    )
}