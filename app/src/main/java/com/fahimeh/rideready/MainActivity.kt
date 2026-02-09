package com.fahimeh.rideready

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fahimeh.rideready.ui.theme.RideReadyTheme

/**
 * Haupt-Activity der App.
 *
 * Verantwortlich nur für:
 * - Initialisierung der UI
 * - Setzen des App-Themes
 * - Starten der Compose-Hierarchie
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RideReadyTheme {
                RideReadyApp()
            }
        }
    }
}