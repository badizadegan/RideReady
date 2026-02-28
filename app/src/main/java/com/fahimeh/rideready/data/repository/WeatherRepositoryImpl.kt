package com.fahimeh.rideready.data.repository

import com.fahimeh.rideready.core.error.AppError
import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.data.local.cache.ForecastCacheLocalDataSource
import com.fahimeh.rideready.data.remote.api.OpenMeteoParams
import com.fahimeh.rideready.data.remote.api.WeatherApiService
import com.fahimeh.rideready.data.remote.mapper.ForecastMapper
import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.repository.WeatherRepository
import java.io.IOException
import java.util.Locale

/**
 * Konkrete Implementierung des WeatherRepository.
 *
 * Holt Daten von Open-Meteo und mappt sie in Domain-Modelle.
 * Bei Fehlern wird ein lokaler Cache als Fallback verwendet.
 */
class WeatherRepositoryImpl(
    private val apiService: WeatherApiService,
    private val cache: ForecastCacheLocalDataSource
) : WeatherRepository {

    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): AppResult<List<ForecastDay>> {

        // Einfacher Key pro Location
        val cacheKey = buildCacheKey(latitude, longitude)

        return try {
            val response = apiService.getForecast(
                latitude = latitude,
                longitude = longitude,
                hourly = OpenMeteoParams.HOURLY,
                daily = OpenMeteoParams.DAILY
            )

            // Cache nach erfolgreichem Call aktualisieren
            cache.save(cacheKey, response)

            val forecastDays = ForecastMapper.toForecastDays(response)

            AppResult.Success(forecastDays)

        } catch (e: IOException) {
            // Fallback: versuche Cache zu laden
            val cached = cache.load(cacheKey)
            if (cached != null) {
                val forecastDays = ForecastMapper.toForecastDays(cached)
                AppResult.Success(forecastDays)
            } else {
                AppResult.Error(AppError.Network)
            }

        } catch (e: Exception) {

            // Fallback auch bei Unknown (z.B. Parsing etc.)
            val cached = cache.load(cacheKey)
            if (cached != null) {
                val forecastDays = ForecastMapper.toForecastDays(cached)
                AppResult.Success(forecastDays)
            } else {
                AppResult.Error(AppError.Unknown(e.message))
            }
        }
    }

    private fun buildCacheKey(lat: Double, lon: Double): String {
        // Rundung verhindert zu viele Keys durch minimale GPS-Abweichungen
        return String.format(Locale.US, "%.4f,%.4f", lat, lon)
    }
}
