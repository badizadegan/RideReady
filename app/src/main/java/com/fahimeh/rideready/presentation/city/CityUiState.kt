package com.fahimeh.rideready.presentation.city

import com.fahimeh.rideready.domain.model.City

/**
 * UI-Zustand für Cities.
 *
 * Enthält nur Domain-Modelle.
 */
data class CityUiState(
    val cities: List<City> = emptyList(),
    val isAddCityDialogVisible: Boolean = false,
    val citySearchQuery: String = "",
    val citySuggestions: List<City> = emptyList(),
    val isSearching: Boolean = false,
    val addCityError: String? = null
)
