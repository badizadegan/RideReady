package com.fahimeh.rideready.data.remote.dto

import com.squareup.moshi.Json

/**
 * DTOs für Open-Meteo Forecast API.
 *
 * Enthält Tages- und Stundenwerte.
 */
data class ForecastResponseDto(
    @Json(name = "timezone")
    val timezone: String? = null,

    @Json(name = "hourly")
    val hourly: HourlyDto? = null,

    @Json(name = "daily")
    val daily: DailyDto? = null
)