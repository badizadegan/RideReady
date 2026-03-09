package com.fahimeh.rideready.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahimeh.rideready.domain.model.TemperatureUnit
import com.fahimeh.rideready.domain.usecase.ObserveSettingsUseCase
import com.fahimeh.rideready.domain.usecase.UpdateTemperatureUnitUseCase
import com.fahimeh.rideready.domain.usecase.UpdateTimeWindowHoursUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel für den Settings-Screen.
 *
 * Beobachtet die gespeicherten Werte
 * und speichert Änderungen.
 */
class SettingsViewModel(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val updateTemperatureUnitUseCase: UpdateTemperatureUnitUseCase,
    private val updateTimeWindowHoursUseCase: UpdateTimeWindowHoursUseCase
) : ViewModel() {

    // Interner Zustand
    private val _uiState = MutableStateFlow(SettingsUiState())

    // Öffentlicher Zustand für die UI
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        // Beobachtet die Einstellungen aus DataStore
        viewModelScope.launch {
            observeSettingsUseCase().collect { settings ->
                _uiState.value = SettingsUiState(settings = settings)
            }
        }
    }

    /**
     * Aktualisiert die Temperatur-Einheit.
     */
    fun updateTemperatureUnit(unit: TemperatureUnit) {
        viewModelScope.launch {
            updateTemperatureUnitUseCase(unit.name)
        }
    }

    /**
     * Aktualisiert die Zeitfenster-Länge.
     */
    fun updateTimeWindow(hours: Int) {
        viewModelScope.launch {
            updateTimeWindowHoursUseCase(hours)
        }
    }
}