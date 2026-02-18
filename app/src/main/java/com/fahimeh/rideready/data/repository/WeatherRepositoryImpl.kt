package com.fahimeh.rideready.data.repository

import com.fahimeh.rideready.core.error.AppError
import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.data.remote.api.WeatherApiService
import com.fahimeh.rideready.data.remote.mapper.ForecastMapper
import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.repository.WeatherRepository
import java.io.IOException

/**
 * Konkrete Implementierung des WeatherRepository.
 *
 * Holt Daten von Open-Meteo und mappt sie in Domain-Modelle.
 */
class WeatherRepositoryImpl(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): AppResult<List<ForecastDay>> {

        return try {
            val response = apiService.getForecast(
                latitude = latitude,
                longitude = longitude,
                hourly = "temperature_2m,precipitation,wind_speed_10m",
                daily = "temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max"
            )

            val forecastDays = ForecastMapper.toForecastDays(response)

            AppResult.Success(forecastDays)

        } catch (e: IOException) {
            AppResult.Error(AppError.Network)
        } catch (e: Exception) {
            AppResult.Error(AppError.Unknown(e.message))
        }
    }
}
