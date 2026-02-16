package com.fahimeh.rideready.domain.model

import java.time.LocalDateTime

/**
 * Stundenwerte für Detailansicht und Best-Time-Window.
 */
data class HourlyForecast(
    val time: LocalDateTime,
    val temperatureC: Double,
    val precipitationMm: Double,
    val windSpeedKmh: Double
)
