package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.HourlyForecast
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Testet, ob das beste Zeitfenster gefunden wird.
 */
class FindBestTimeWindowUseCaseTest {

    private val useCase = FindBestTimeWindowUseCase()

    @Test
    fun returnsBestTwoHourWindow() {
        val hourly = listOf(
            HourlyForecast(LocalDateTime.of(2026, 3, 10, 6, 0), 10.0, 0.0, 8.0),
            HourlyForecast(LocalDateTime.of(2026, 3, 10, 7, 0), 11.0, 0.0, 7.0),
            HourlyForecast(LocalDateTime.of(2026, 3, 10, 8, 0), 12.0, 2.5, 25.0),
            HourlyForecast(LocalDateTime.of(2026, 3, 10, 9, 0), 13.0, 1.0, 20.0)
        )

        val day = ForecastDay(
            date = LocalDate.of(2026, 3, 10),
            minTempC = 8.0,
            maxTempC = 14.0,
            precipitationMm = 0.0,
            windSpeedKmh = 10.0,
            hourly = hourly
        )

        val result = useCase(day, windowHours = 2)

        assertNotNull(result)

        // result.first = TimeWindow
        assertEquals(6, result?.first?.start?.hour)
        assertEquals(8, result?.first?.end?.hour)
    }
}