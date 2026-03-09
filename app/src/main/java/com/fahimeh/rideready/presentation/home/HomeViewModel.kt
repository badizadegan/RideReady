package com.fahimeh.rideready.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahimeh.rideready.core.error.AppError
import com.fahimeh.rideready.core.forecast.ForecastMemoryStore
import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.domain.usecase.FindBestDayUseCase
import com.fahimeh.rideready.domain.usecase.GetForecastUseCase
import com.fahimeh.rideready.domain.usecase.ObserveSelectedCityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * ViewModel für den HomeScreen.
 *
 * Lädt Forecast-Daten über UseCases
 * und stellt sie als UiState für die UI bereit.
 *
 * Die UI kennt keine Repository- oder API-Details.
 */
class HomeViewModel(
    private val getForecastUseCase: GetForecastUseCase,
    private val findBestDayUseCase: FindBestDayUseCase,
    private val memoryStore: ForecastMemoryStore,
    private val observeSelectedCityUseCase: ObserveSelectedCityUseCase
) : ViewModel() {

    // Interner Zustand, wird nur im ViewModel geändert.
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    // Öffentlicher, nur lesbarer Zustand für die UI.
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Hält den Namen der aktuell ausgewählten Stadt
    private var currentCityName: String = "Leipzig"

    init {
        // Beobachtet die ausgewählte Stadt aus der Datenbank.
        // Wenn der Nutzer eine andere Stadt auswählt,
        // wird automatisch ein neuer Forecast geladen.
        observeSelectedCity()
    }

    private fun observeSelectedCity() {
        viewModelScope.launch {

            observeSelectedCityUseCase()
                .distinctUntilChanged()
                .collect { city ->

                // fallback falls noch keine Stadt ausgewählt ist
                    currentCityName = city?.name ?: "Leipzig"

                val latitude = city?.latitude ?: 51.3397
                val longitude = city?.longitude ?: 12.3731

                loadForecast(latitude, longitude)
            }
        }
    }

    /**
     * Lädt den Wetter-Forecast erneut.
     * Wird vom Retry-Button im UI aufgerufen.
     */
    fun refresh() {
        viewModelScope.launch {
            observeSelectedCityUseCase().firstOrNull()?.let { city ->
                val lat = city.latitude
                val lon = city.longitude
                loadForecast(lat, lon)
            }
        }
    }

    private suspend fun loadForecast(latitude: Double, longitude: Double) {

        _uiState.value = HomeUiState.Loading

        when (val result = getForecastUseCase(latitude, longitude)) {
                is AppResult.Success -> {
                    val days = result.data

                    // Speichert die aktuell geladenen Forecast-Daten im Memory Store
                    memoryStore.update(days)

                    val best = findBestDayUseCase(days)

                    _uiState.value =
                        if (days.isEmpty()) HomeUiState.Empty
                        else HomeUiState.Success(
                            selectedCityName = currentCityName,
                            days = days,
                            bestDay = best?.first,
                            bestScore = best?.second
                        )
                }
                is AppResult.Error -> {
                    _uiState.value = HomeUiState.Error(mapError(result.error))
                }
                AppResult.Loading -> {
                    _uiState.value = HomeUiState.Loading
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
