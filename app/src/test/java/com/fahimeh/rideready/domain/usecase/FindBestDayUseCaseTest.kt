package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.DayOfWeek
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

    @Test
    fun preferredDaysEmpty_keepsExistingBehavior() {
        val tuesday = ForecastDay(
            date = LocalDate.of(2026, 4, 14),
            minTempC = 10.0,
            maxTempC = 15.0,
            precipitationMm = 4.0,
            windSpeedKmh = 20.0,
            hourly = emptyList()
        )
        val wednesday = ForecastDay(
            date = LocalDate.of(2026, 4, 15),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 0.0,
            windSpeedKmh = 10.0,
            hourly = emptyList()
        )

        val unrestricted = useCase(listOf(tuesday, wednesday))
        val explicitlyEmpty = useCase(
            days = listOf(tuesday, wednesday),
            preferredDays = emptySet()
        )

        assertEquals(unrestricted?.first?.date, explicitlyEmpty?.first?.date)
        assertEquals(unrestricted?.second?.score, explicitlyEmpty?.second?.score)
    }

    @Test
    fun singlePreferredDay_onlyConsidersMatchingWeekday() {
        val monday = ForecastDay(
            date = LocalDate.of(2026, 4, 13),
            minTempC = 5.0,
            maxTempC = 10.0,
            precipitationMm = 8.0,
            windSpeedKmh = 35.0,
            hourly = emptyList()
        )
        val tuesday = ForecastDay(
            date = LocalDate.of(2026, 4, 14),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 0.0,
            windSpeedKmh = 10.0,
            hourly = emptyList()
        )

        val result = useCase(
            days = listOf(monday, tuesday),
            preferredDays = setOf(DayOfWeek.MONDAY)
        )

        assertEquals(monday.date, result?.first?.date)
    }

    @Test
    fun multiplePreferredDays_onlyConsidersThoseWeekdays() {
        val monday = ForecastDay(
            date = LocalDate.of(2026, 4, 13),
            minTempC = 8.0,
            maxTempC = 12.0,
            precipitationMm = 4.0,
            windSpeedKmh = 25.0,
            hourly = emptyList()
        )
        val tuesday = ForecastDay(
            date = LocalDate.of(2026, 4, 14),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 0.0,
            windSpeedKmh = 8.0,
            hourly = emptyList()
        )
        val wednesday = ForecastDay(
            date = LocalDate.of(2026, 4, 15),
            minTempC = 18.0,
            maxTempC = 24.0,
            precipitationMm = 0.0,
            windSpeedKmh = 8.0,
            hourly = emptyList()
        )

        val result = useCase(
            days = listOf(monday, tuesday, wednesday),
            preferredDays = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)
        )

        assertEquals(wednesday.date, result?.first?.date)
    }

    @Test
    fun noMatchingPreferredDay_returnsNullSafely() {
        val monday = ForecastDay(
            date = LocalDate.of(2026, 4, 13),
            minTempC = 14.0,
            maxTempC = 22.0,
            precipitationMm = 0.0,
            windSpeedKmh = 10.0,
            hourly = emptyList()
        )

        val result = useCase(
            days = listOf(monday),
            preferredDays = setOf(DayOfWeek.TUESDAY)
        )

        assertNull(result)
    }
}
