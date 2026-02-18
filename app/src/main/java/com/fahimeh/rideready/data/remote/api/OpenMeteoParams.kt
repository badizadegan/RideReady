package com.fahimeh.rideready.data.remote.api

/**
 * Enthält die Parameter-Listen für Open-Meteo.
 *
 * So werden keine Strings direkt im Repository verwendet.
 */
object OpenMeteoParams {
    const val HOURLY = "temperature_2m,precipitation,wind_speed_10m"
    const val DAILY = "temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max"
}