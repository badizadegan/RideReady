package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.repository.WeatherRepository

/**
 * UseCase zum Laden der Forecast-Daten.
 *
 * Die Präsentationsschicht ruft nur UseCases auf,
 * nicht direkt das Repository.
 */
class GetForecastUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): AppResult<List<ForecastDay>> {
        return repository.getForecast(latitude, longitude)
    }
}