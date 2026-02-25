package com.fahimeh.rideready.data.remote.mapper

import com.fahimeh.rideready.data.remote.dto.ForecastResponseDto
import com.fahimeh.rideready.data.remote.dto.HourlyDto
import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.HourlyForecast
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.collections.orEmpty

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

        val allHourly = dto.hourly?.toHourlyForecasts().orEmpty()

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
                hourly = allHourly.filter { it.time.toLocalDate() == date }
            )
        }
    }
}

/**
 * Erstellt HourlyForecast aus parallelen Arrays.
 */
private fun HourlyDto.toHourlyForecasts(): List<HourlyForecast> {

    val size = minOf(
        time.size,
        temperature2m.size,
        precipitation.size,
        windSpeed10m.size
    )

    return (0 until size).mapNotNull { index ->
        val parsedTime = runCatching { LocalDateTime.parse(time[index]) }.getOrNull()
            ?: return@mapNotNull null

        HourlyForecast(
            time = parsedTime,
            temperatureC = temperature2m[index],
            precipitationMm = precipitation[index],
            windSpeedKmh = windSpeed10m[index]
        )
    }
}
