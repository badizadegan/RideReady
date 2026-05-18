package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideMode
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

    @Test
    fun usesRideModeWhenSelectingBestDay() {
        val breezyLightRainDay = ForecastDay(
            date = LocalDate.of(2026, 4, 10),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 2.0,
            windSpeedKmh = 25.0,
            hourly = emptyList()
        )

        val hotCalmDay = ForecastDay(
            date = LocalDate.of(2026, 4, 11),
            minTempC = 24.0,
            maxTempC = 34.0,
            precipitationMm = 0.0,
            windSpeedKmh = 8.0,
            hourly = emptyList()
        )

        val bikeResult = useCase(listOf(breezyLightRainDay, hotCalmDay), RideMode.BIKE)
        val runResult = useCase(listOf(breezyLightRainDay, hotCalmDay), RideMode.RUN)

        assertEquals(hotCalmDay.date, bikeResult?.first?.date)
        assertEquals(breezyLightRainDay.date, runResult?.first?.date)
    }

    @Test
    fun usesPreferredTemperatureRangeWhenSelectingBestDay() {
        val mildDay = ForecastDay(
            date = LocalDate.of(2026, 4, 12),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 0.0,
            windSpeedKmh = 8.0,
            hourly = emptyList()
        )

        val warmDay = ForecastDay(
            date = LocalDate.of(2026, 4, 13),
            minTempC = 20.0,
            maxTempC = 28.0,
            precipitationMm = 0.0,
            windSpeedKmh = 8.0,
            hourly = emptyList()
        )

        val result = useCase(
            days = listOf(mildDay, warmDay),
            rideMode = RideMode.BIKE,
            preferredMinTemp = 20,
            preferredMaxTemp = 26
        )

        assertEquals(warmDay.date, result?.first?.date)
    }
}
