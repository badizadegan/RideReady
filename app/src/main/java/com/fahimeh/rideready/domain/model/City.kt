package com.fahimeh.rideready.domain.model

/**
 * Domain-Modell für eine Stadt.
 * Die Domain kennt keine Room-Entities.
 */
data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double
)