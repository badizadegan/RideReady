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
import kotlinx.coroutines.flow.update
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
                _uiState.update { state ->
                    state.copy(
                        cities = list,
                        citySuggestions = findCitySuggestions(
                            query = state.citySearchQuery,
                            savedCities = list
                        )
                    )
                }
            }
        }
    }

    private val predefinedCities = listOf(
        City(name = "Leipzig", latitude = 51.3397, longitude = 12.3731),
        City(name = "Berlin", latitude = 52.5200, longitude = 13.4050),
        City(name = "Munich", latitude = 48.1351, longitude = 11.5820),
        City(name = "Hamburg", latitude = 53.5511, longitude = 9.9937),
        City(name = "Cologne", latitude = 50.9375, longitude = 6.9603),
        City(name = "Frankfurt", latitude = 50.1109, longitude = 8.6821),
        City(name = "Stuttgart", latitude = 48.7758, longitude = 9.1829),
        City(name = "Dusseldorf", latitude = 51.2277, longitude = 6.7735)
    )

    fun showAddCityDialog() {
        _uiState.update { state ->
            state.copy(
                isAddCityDialogVisible = true,
                citySearchQuery = "",
                citySuggestions = findCitySuggestions("", state.cities),
                addCityError = null
            )
        }
    }

    fun dismissAddCityDialog() {
        _uiState.update { state ->
            state.copy(
                isAddCityDialogVisible = false,
                citySearchQuery = "",
                citySuggestions = emptyList(),
                addCityError = null
            )
        }
    }

    fun updateCitySearchQuery(query: String) {
        _uiState.update { state ->
            state.copy(
                citySearchQuery = query,
                citySuggestions = findCitySuggestions(query, state.cities),
                addCityError = null
            )
        }
    }

    fun addCity(city: City) {
        viewModelScope.launch {
            val currentCities = _uiState.value.cities

            if (currentCities.any { it.name.equals(city.name, ignoreCase = true) }) {
                _uiState.update { state ->
                    state.copy(addCityError = "${city.name} is already saved.")
                }
                return@launch
            }

            saveCityUseCase(city)
            dismissAddCityDialog()
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

    private fun findCitySuggestions(
        query: String,
        savedCities: List<City>
    ): List<City> {
        val savedNames = savedCities.map { it.name.lowercase() }.toSet()
        val normalizedQuery = query.trim()

        return predefinedCities
            .filterNot { it.name.lowercase() in savedNames }
            .filter { city ->
                normalizedQuery.isBlank() ||
                    city.name.contains(normalizedQuery, ignoreCase = true)
            }
    }
}
