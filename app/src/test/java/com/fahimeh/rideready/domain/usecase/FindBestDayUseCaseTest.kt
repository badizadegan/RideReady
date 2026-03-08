package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDate

/**
 * Testet, ob der beste Tag korrekt ausgewählt wird.
 */
class FindBestDayUseCaseTest {

    private val scoreUseCase = CalculateRideScoreUseCase()
    private val useCase = FindBestDayUseCase(scoreUseCase)

    @Test
    fun returnsDayWithHighestScore() {
        val badDay = ForecastDay(
            date = LocalDate.of(2026, 3, 10),
            minTempC = 5.0,
            maxTempC = 9.0,
            precipitationMm = 7.0,
            windSpeedKmh = 35.0,
            hourly = emptyList()
        )

        val goodDay = ForecastDay(
            date = LocalDate.of(2026, 3, 11),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 0.0,
            windSpeedKmh = 10.0,
            hourly = emptyList()
        )

        val result = useCase(listOf(badDay, goodDay))

        assertNotNull(result)
        assertEquals(goodDay.date, result?.first?.date)
    }
}