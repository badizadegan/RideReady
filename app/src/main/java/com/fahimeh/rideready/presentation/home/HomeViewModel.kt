package com.fahimeh.rideready.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahimeh.rideready.core.error.AppError
import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel für den HomeScreen.
 *
 * Aufgabe:
 *  * - Holt die Forecast-Daten über den UseCase
 *  * - Verwaltet den aktuellen UI-Zustand
 *  * - Wandelt technische Ergebnisse (AppResult) in darstellbare UiStates um
 *  *
 *  * Wichtig:
 *  * Die UI kennt keine Repository- oder API-Details.
 */
class HomeViewModel(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    /**
     * Interner, veränderbarer Zustand.
     * Nur das ViewModel darf diesen ändern.
     */
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    /**
     * Öffentlicher, nur lesbarer Zustand für die UI.
     * Der Screen beobachtet diesen State und rendert entsprechend.
     */
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun loadForecast() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            // TODO: Später aus gespeicherten Städten / Settings holen.
            val latitude = 51.3397
            val longitude = 12.3731

            when (val result = getForecastUseCase(latitude, longitude)) {
                is AppResult.Success -> {
                    val days = result.data
                    _uiState.value = if (days.isEmpty()) HomeUiState.Empty else HomeUiState.Success(days)
                }
                is AppResult.Error -> {
                    _uiState.value = HomeUiState.Error(mapError(result.error))
                }
                AppResult.Loading -> {
                    _uiState.value = HomeUiState.Loading
                }
            }
        }
    }

    /**
     * Wandelt interne Fehler-Typen in einfache Textmeldungen um,
     * die im UI angezeigt werden können.
     */
    private fun mapError(error: AppError): String {
        return when (error) {
            AppError.Network -> "No internet connection"
            AppError.Server -> "Server error"
            is AppError.Unknown -> error.message ?: "Unknown error"
        }
    }
}
