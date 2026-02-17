package com.fahimeh.rideready.data.remote.dto

import com.squareup.moshi.Json

/**
 * Repräsentiert die täglichen aggregierten Wetterdaten.
 */
data class DailyDto(
    @Json(name = "time")
    val time: List<String> = emptyList(),

    @Json(name = "temperature_2m_min")
    val tempMin: List<Double> = emptyList(),

    @Json(name = "temperature_2m_max")
    val tempMax: List<Double> = emptyList(),

    @Json(name = "precipitation_sum")
    val precipitationSum: List<Double> = emptyList(),

    @Json(name = "windspeed_10m_max")
    val windSpeedMax: List<Double> = emptyList()
)