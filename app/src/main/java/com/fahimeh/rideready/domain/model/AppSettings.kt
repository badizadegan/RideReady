package com.fahimeh.rideready.domain.model

/**
 * Domain-Modell für einfache App-Einstellungen.
 */
data class AppSettings(
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val timeWindowHours: Int = 2
)

// Temperatur-Einheit für die Anzeige
enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
}