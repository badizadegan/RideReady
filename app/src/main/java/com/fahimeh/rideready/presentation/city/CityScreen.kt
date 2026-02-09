package com.fahimeh.rideready.presentation.city

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Verwaltung gespeicherter Städte.
 *
 * Platzhalter-Version.
 */
@Composable
fun CityScreen(
    onBack: () -> Unit
) {
    Text(text = "Cities Screen")
}

@Composable
@Preview(showBackground = true)
fun CityScreenPreview() {
    CityScreen(
        onBack = {}
    )
}