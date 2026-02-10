package com.fahimeh.rideready.data.remote.dto

/**
 * Datenklasse für die rohe API-Antwort.
 *
 * Wird später in Domain-Modelle gemappt.
 */
data class ForecastResponseDto(
    val timezone: String? = null
)