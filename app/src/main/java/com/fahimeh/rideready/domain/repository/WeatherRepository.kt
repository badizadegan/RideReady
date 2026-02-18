package com.fahimeh.rideready.domain.repository

import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.domain.model.ForecastDay

/**
 * Abstraktion für Wetterdaten.
 *
 * Die Domain kennt nur dieses Interface,
 * nicht die konkrete API-Implementierung.
 */
interface WeatherRepository {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): AppResult<List<ForecastDay>>
}