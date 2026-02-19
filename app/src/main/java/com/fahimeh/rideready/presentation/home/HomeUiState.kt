package com.fahimeh.rideready.presentation.home

import com.fahimeh.rideready.domain.model.ForecastDay

/**
 * UI-Zustände für den HomeScreen.
 *
 * Der Screen reagiert ausschließlich auf diesen State
 * und enthält keinerlei Business-Logik.
 */
sealed class HomeUiState {

    /**
     * Daten werden geladen
     */
    object Loading : HomeUiState()

    /**
     * Erfolgreiche Anzeige mit Daten
     */
    data class Success(
        val days: List<ForecastDay>
    ) : HomeUiState()

    /**
     * Fehlerzustand mit Anzeige für den User
     */
    data class Error(
        val message: String
    ) : HomeUiState()

    /**
     * Leerer Zustand (z. B. keine Daten vorhanden)
     */
    object Empty : HomeUiState()
}