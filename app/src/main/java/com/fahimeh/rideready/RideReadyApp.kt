package com.fahimeh.rideready

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.fahimeh.rideready.presentation.navigation.RideReadyNavHost

/**
 * Einstiegspunkt der Compose UI.
 */
@Composable
fun RideReadyApp() {
    MaterialTheme {
        RideReadyNavHost()
    }
}