package com.fahimeh.rideready.domain.model

/**
 * Domain-Modell für einfache App-Einstellungen.
 */
data class AppSettings(
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val timeWindowHours: Int = 2,
    val selectedRideMode: RideMode = RideMode.BIKE,
    val availableStartHour: Int = DEFAULT_AVAILABLE_START_HOUR,
    val availableEndHour: Int = DEFAULT_AVAILABLE_END_HOUR
) {
    companion object {
        const val DEFAULT_AVAILABLE_START_HOUR = 6
        const val DEFAULT_AVAILABLE_END_HOUR = 22
    }
}

// Temperatur-Einheit für die Anzeige
enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
}
