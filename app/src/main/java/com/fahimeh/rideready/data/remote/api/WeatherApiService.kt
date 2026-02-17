package com.fahimeh.rideready.data.remote.api

import com.fahimeh.rideready.data.remote.dto.ForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API-Schnittstelle für Open-Meteo.
 *
 * Holt Wetterdaten als Input für die Entscheidungslogik.
 * Die UI kennt diese Schnittstelle nicht direkt.
 */
interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,

        // Welche stündlichen Werte sollen geliefert werden?
        @Query("hourly") hourly: String,

        // Welche täglichen Werte sollen geliefert werden?
        @Query("daily") daily: String,

        // Zeitzone automatisch anhand der Koordinaten
        @Query("timezone") timezone: String = "auto",

        // Einheiten
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("wind_speed_unit") windSpeedUnit: String = "kmh",
        @Query("precipitation_unit") precipitationUnit: String = "mm",

        // Forecast-Länge
        @Query("forecast_days") forecastDays: Int = 7
    ): ForecastResponseDto
}
