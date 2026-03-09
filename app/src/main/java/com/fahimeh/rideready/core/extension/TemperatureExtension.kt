package com.fahimeh.rideready.core.extension

import com.fahimeh.rideready.domain.model.TemperatureUnit
import kotlin.math.roundToInt

/**
 * Formatiert eine Temperatur für die UI.
 */
fun formatTemperature(valueC: Double, unit: TemperatureUnit): String {
    return when (unit) {
        TemperatureUnit.CELSIUS -> "${valueC.roundToInt()}°C"
        TemperatureUnit.FAHRENHEIT -> "${((valueC * 9 / 5) + 32).roundToInt()}°F"
    }
}