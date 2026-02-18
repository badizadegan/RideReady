package com.fahimeh.rideready.data.remote.dto

import com.squareup.moshi.Json

/**
 * Repräsentiert die stündlichen Wetterdaten.
 */
data class HourlyDto(
    @Json(name = "time")
    val time: List<String> = emptyList(),

    @Json(name = "temperature_2m")
    val temperature2m: List<Double> = emptyList(),

    @Json(name = "precipitation")
    val precipitation: List<Double> = emptyList(),

    @Json(name = "wind_speed_10m")
    val windSpeed10m: List<Double> = emptyList()
)