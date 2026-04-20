package com.fahimeh.rideready.presentation.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.usecase.DeleteCityUseCase
import com.fahimeh.rideready.domain.usecase.GetSavedCitiesUseCase
import com.fahimeh.rideready.domain.usecase.SaveCityUseCase
import com.fahimeh.rideready.domain.usecase.SearchCityUseCase
import com.fahimeh.rideready.domain.usecase.SelectCityUseCase
import kotlinx.coroutines.Job
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
    private val selectCityUseCase: SelectCityUseCase,
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    // UI-Zustand wird hier zentral gehalten
    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState.asStateFlow()
    private var searchJob: Job? = null

    /**
     * Startet das Sammeln des Flow aus der Datenbank.
     */
    init {
        viewModelScope.launch {
            getSavedCitiesUseCase().collect { list ->
                _uiState.update { state ->
                    state.copy(
                        cities = list,
                        citySuggestions = removeSavedCities(state.citySuggestions, list)
                    )
                }
            }
        }
    }

    fun showAddCityDialog() {
        _uiState.update { state ->
            state.copy(
                isAddCityDialogVisible = true,
                citySearchQuery = "",
                citySuggestions = emptyList(),
                isSearching = false,
                addCityError = null
            )
        }
    }

    fun dismissAddCityDialog() {
        searchJob?.cancel()
        _uiState.update { state ->
            state.copy(
                isAddCityDialogVisible = false,
                citySearchQuery = "",
                citySuggestions = emptyList(),
                isSearching = false,
                addCityError = null
            )
        }
    }

    fun updateCitySearchQuery(query: String) {
        val trimmedQuery = query.trim()

        _uiState.update { state ->
            state.copy(
                citySearchQuery = query,
                citySuggestions = if (trimmedQuery.isBlank()) emptyList() else state.citySuggestions,
                isSearching = false,
                addCityError = null
            )
        }

        if (trimmedQuery.isNotBlank()) {
            searchCities(trimmedQuery)
        } else {
            searchJob?.cancel()
        }
    }

    fun addCity(city: City) {
        viewModelScope.launch {
            val currentCities = _uiState.value.cities

            if (currentCities.any { it.isSameCity(city) }) {
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

    private fun searchCities(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            searchCityUseCase(query).collect { result ->
                when (result) {
                    AppResult.Loading -> {
                        _uiState.update { state ->
                            state.copy(
                                isSearching = true,
                                addCityError = null
                            )
                        }
                    }
                    is AppResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                citySuggestions = removeSavedCities(result.data, state.cities),
                                isSearching = false,
                                addCityError = null
                            )
                        }
                    }
                    is AppResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                citySuggestions = emptyList(),
                                isSearching = false,
                                addCityError = "Could not search cities."
                            )
                        }
                    }
                }
            }
        }
    }

    private fun removeSavedCities(
        cities: List<City>,
        savedCities: List<City>
    ): List<City> {
        return cities.filterNot { city ->
            savedCities.any { savedCity -> savedCity.isSameCity(city) }
        }
    }

    private fun City.isSameCity(other: City): Boolean {
        return name.equals(other.name, ignoreCase = true) &&
            latitude == other.latitude &&
            longitude == other.longitude
    }
}
