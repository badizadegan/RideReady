package com.fahimeh.rideready.data.repository

import com.fahimeh.rideready.core.error.AppError
import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.data.remote.api.OpenMeteoParams
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
                hourly = OpenMeteoParams.HOURLY,
                daily = OpenMeteoParams.DAILY
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
