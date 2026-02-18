package com.fahimeh.rideready.data.remote.mapper

import com.fahimeh.rideready.data.remote.dto.ForecastResponseDto
import com.fahimeh.rideready.domain.model.ForecastDay
import java.time.LocalDate

/**
 * Wandelt die Open-Meteo DTOs in Domain-Modelle um.
 *
 * Hier wird die API-Struktur in eine Form gebracht,
 * die die App intern verwenden kann.
 */
object ForecastMapper {

    fun toForecastDays(dto: ForecastResponseDto): List<ForecastDay> {
        val daily = dto.daily ?: return emptyList()

        val dates = daily.time.mapNotNull { runCatching { LocalDate.parse(it) }.getOrNull() }

        val size = minOf(
            dates.size,
            daily.tempMin.size,
            daily.tempMax.size,
            daily.precipitationSum.size,
            daily.windSpeedMax.size
        )

        return (0 until size).map { index ->
            val date = dates[index]
            ForecastDay(
                date = date,
                minTempC = daily.tempMin[index],
                maxTempC = daily.tempMax[index],
                precipitationMm = daily.precipitationSum[index],
                windSpeedKmh = daily.windSpeedMax[index],
                hourly = emptyList()
            )
        }
    }
}
