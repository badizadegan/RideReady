package com.fahimeh.rideready.presentation.city

import com.fahimeh.rideready.data.local.entity.CityEntity

/**
 * UI-Zustand für Cities.
 */
data class CityUiState(
    val cities: List<CityEntity> = emptyList()
)