package com.fahimeh.rideready.presentation.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.usecase.DeleteCityUseCase
import com.fahimeh.rideready.domain.usecase.GetSavedCitiesUseCase
import com.fahimeh.rideready.domain.usecase.SaveCityUseCase
import com.fahimeh.rideready.domain.usecase.SelectCityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel für CitiesScreen.
 *
 * Beobachtet gespeicherte Städte
 * und bietet Aktionen zum Speichern/Löschen.
 *
 * Arbeitet nur mit dem Domain-Modell City
 * und kennt keine Room-Entity mehr.
 */
class CityViewModel(
    private val getSavedCitiesUseCase: GetSavedCitiesUseCase,
    private val saveCityUseCase: SaveCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val selectCityUseCase: SelectCityUseCase
) : ViewModel() {

    // UI-Zustand wird hier zentral gehalten
    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState.asStateFlow()

    /**
     * Startet das Sammeln des Flow aus der Datenbank.
     */
    init {
        viewModelScope.launch {
            getSavedCitiesUseCase().collect { list ->
                _uiState.value = CityUiState(cities = list)
            }
        }
    }

    /**
     * Fügt testweise eine Beispielstadt hinzu.
     * Wird später durch echte Suche ersetzt.
     */
    private val dummyCities = listOf(
        City(name = "Leipzig", latitude = 51.3397, longitude = 12.3731),
        City(name = "Berlin", latitude = 52.5200, longitude = 13.4050),
        City(name = "Munich", latitude = 48.1351, longitude = 11.5820),
        City(name = "Hamburg", latitude = 53.5511, longitude = 9.9937)
    )

    fun addDummyCity() {
        viewModelScope.launch {
            val city = dummyCities.random()
            saveCityUseCase(city)
        }
    }

    /**
     * Löscht eine Stadt.
     */
    fun deleteCity(city: City) {
        viewModelScope.launch {
            deleteCityUseCase(city)
        }
    }

    // Wird aufgerufen wenn User eine Stadt auswählt
    fun selectCity(cityId: Long) {
        viewModelScope.launch {
            selectCityUseCase(cityId)
        }
    }
}