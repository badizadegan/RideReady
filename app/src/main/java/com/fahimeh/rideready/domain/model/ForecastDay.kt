package com.fahimeh.rideready.domain.model

import java.time.LocalDate

/**
 * Repräsentiert eine Tagesvorhersage in der Domain.
 *
 * Wichtig:
 * - Keine Android- oder Netzwerk-Typen
 * - Nur fachliche Felder
 */
data class ForecastDay(
    val date: LocalDate,
    val minTempC: Double,
    val maxTempC: Double,
    val precipitationMm: Double,
    val windSpeedKmh: Double,
    val hourly: List<HourlyForecast> = emptyList()
)
