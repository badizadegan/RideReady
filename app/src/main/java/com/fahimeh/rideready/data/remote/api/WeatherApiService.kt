package com.fahimeh.rideready.data.remote.api

import com.fahimeh.rideready.data.remote.dto.ForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API-Definition für Wetterdaten.
 *
 * Diese Schnittstelle beschreibt ausschließlich,
 * welche Endpunkte existieren und welche Parameter benötigt werden.
 */
interface WeatherApiService {

    @GET("forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): ForecastResponseDto
}
