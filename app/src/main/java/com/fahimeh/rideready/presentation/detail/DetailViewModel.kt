package com.fahimeh.rideready.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahimeh.rideready.core.forecast.ForecastMemoryStore
import com.fahimeh.rideready.domain.model.AppSettings
import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideScoreResult
import com.fahimeh.rideready.domain.model.TimeWindow
import com.fahimeh.rideready.domain.usecase.FindBestTimeWindowUseCase
import com.fahimeh.rideready.domain.usecase.ObserveSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel für die Detailansicht.
 *
 * Liest den ausgewählten Tag aus einem In-Memory Store.
 */
class DetailViewModel(
    private val memoryStore: ForecastMemoryStore,
    private val findBestTimeWindowUseCase: FindBestTimeWindowUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase
) : ViewModel() {

    // Hält die aktuellen App-Einstellungen
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init {
        // Beobachtet die Einstellungen aus DataStore
        viewModelScope.launch {
            observeSettingsUseCase().collect { appSettings ->
                _settings.value = appSettings
            }
        }
    }

    /**
     * Sucht den passenden Forecast-Tag über das Datum.
     */
    fun getDay(dateString: String): ForecastDay? {
        val date = runCatching { LocalDate.parse(dateString) }.getOrNull()
            ?: return null

        return memoryStore.days.firstOrNull { it.date == date }
    }

    /**
     * Berechnet das beste Zeitfenster
     * mit der aktuell eingestellten Fenster-Länge.
     */
    fun getBestWindow(dateString: String, windowHours: Int): Pair<TimeWindow, RideScoreResult>? {
        val day = getDay(dateString) ?: return null
        return findBestTimeWindowUseCase(
            day = day,
            windowHours = windowHours
        )
    }
}