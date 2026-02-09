package com.fahimeh.rideready.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Startbildschirm der App.
 *
 * Aktuell als Platzhalter umgesetzt.
 */
@Composable
fun HomeScreen(
    onNavigateToDetail: () -> Unit,
    onNavigateToCities: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Text(text = "Home Screen")
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