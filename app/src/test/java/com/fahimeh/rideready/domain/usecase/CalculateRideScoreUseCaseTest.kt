package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

/**
 * Testet die Score-Berechnung (Business-Logik).
 */
class CalculateRideScoreUseCaseTest {

    private val useCase = CalculateRideScoreUseCase()

    @Test
    fun goodWeather_returnsHighScore() {
        val day = ForecastDay(
            date = LocalDate.of(2026, 3, 10),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 0.0,
            windSpeedKmh = 10.0,
            hourly = emptyList()
        )

        val result = useCase(day)
        assertTrue(result.score >= 80)
    }

    @Test
    fun heavyRain_returnsLowerScore() {
        val day = ForecastDay(
            date = LocalDate.of(2026, 3, 10),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 8.0,
            windSpeedKmh = 10.0,
            hourly = emptyList()
        )

        val result = useCase(day)
        assertTrue(result.score < 80)
    }
}