package com.fahimeh.rideready.presentation.home

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideScoreResult

/**
 * UI-Zustände für den HomeScreen.
 *
 * Der Screen reagiert ausschließlich auf diesen State
 * und enthält keinerlei Business-Logik.
 */
sealed class HomeUiState {

    //Daten werden geladen
    object Loading : HomeUiState()

    //Enfalgreiche Anzeige mit Daten
    data class Success(
        val days: List<ForecastDay>,
        val bestDay: ForecastDay? = null,
        val bestScore: RideScoreResult? = null
    ) : HomeUiState()

    //Echlerzustand mit Anzeige für den User
    data class Error(
        val message: String
    ) : HomeUiState()

    //Leerer Zustand
    object Empty : HomeUiState()
}