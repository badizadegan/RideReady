package com.fahimeh.rideready.presentation.city

import com.fahimeh.rideready.domain.model.City

/**
 * UI-Zustand für Cities.
 *
 * Enthält nur Domain-Modelle.
 */
data class CityUiState(
    val cities: List<City> = emptyList()
)