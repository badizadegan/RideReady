package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideScoreResult

/**
 * Berechnet einen einfachen Score (0..100) für einen Tag.
 */
class CalculateRideScoreUseCase {
    private val idealRange = 15.0..25.0
    private val tolerableRange = 10.0..30.0

    operator fun invoke(day: ForecastDay): RideScoreResult {
        var score = 100

        // Temperatur (ideal: 15–25°C)
        val avgTemp = (day.minTempC + day.maxTempC) / 2
        score -= when {
            avgTemp !in tolerableRange -> 30
            avgTemp !in idealRange -> 15
            else -> 0
        }

        // Niederschlag (mm pro Tag)
        score -= when {
            day.precipitationMm > 5 -> 30
            day.precipitationMm > 1 -> 10
            else -> 0
        }

        // Wind (km/h)
        score -= when {
            day.windSpeedKmh > 30 -> 20
            day.windSpeedKmh > 20 -> 10
            else -> 0
        }

        score = score.coerceIn(0, 100)

        val reason = when {
            day.precipitationMm > 5 -> "High precipitation"
            day.windSpeedKmh > 30 -> "Strong wind"
            avgTemp < 10 -> "Too cold"
            avgTemp > 30 -> "Too hot"
            else -> "Good overall conditions"
        }

        return RideScoreResult(
            score = score,
            reason = reason
        )
    }
}