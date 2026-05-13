package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideMode
import com.fahimeh.rideready.domain.model.RideScoreResult

/**
 * Berechnet einen einfachen Score (0..100) für einen Tag.
 */
class CalculateRideScoreUseCase {
    private val idealRange = 15.0..25.0
    private val tolerableRange = 10.0..30.0

    operator fun invoke(
        day: ForecastDay,
        rideMode: RideMode = RideMode.BIKE
    ): RideScoreResult {
        var score = 100

        val avgTemp = (day.minTempC + day.maxTempC) / 2
        score -= temperaturePenalty(avgTemp, rideMode)
        score -= precipitationPenalty(day.precipitationMm, rideMode)
        score -= windPenalty(day.windSpeedKmh, rideMode)

        score = score.coerceIn(0, 100)

        val reason = when {
            day.precipitationMm > precipitationReasonThreshold(rideMode) -> "High precipitation"
            day.windSpeedKmh > windReasonThreshold(rideMode) -> "Strong wind"
            avgTemp < temperatureLowReasonThreshold(rideMode) -> "Too cold"
            avgTemp > temperatureHighReasonThreshold(rideMode) -> "Too hot"
            else -> "Good overall conditions"
        }

        return RideScoreResult(
            score = score,
            reason = reason
        )
    }

    private fun temperaturePenalty(avgTemp: Double, rideMode: RideMode): Int {
        return when (rideMode) {
            RideMode.BIKE -> when {
                avgTemp !in tolerableRange -> 30
                avgTemp !in idealRange -> 15
                else -> 0
            }
            RideMode.WALK -> when {
                avgTemp < 8.0 || avgTemp > 30.0 -> 25
                avgTemp < 12.0 || avgTemp > 26.0 -> 10
                else -> 0
            }
            RideMode.RUN -> when {
                avgTemp < 10.0 || avgTemp > 28.0 -> 30
                avgTemp < 13.0 || avgTemp > 24.0 -> 20
                else -> 0
            }
        }
    }

    private fun precipitationPenalty(precipitationMm: Double, rideMode: RideMode): Int {
        return when (rideMode) {
            RideMode.BIKE -> when {
                precipitationMm > 5 -> 30
                precipitationMm > 1 -> 10
                else -> 0
            }
            RideMode.WALK -> when {
                precipitationMm > 7 -> 30
                precipitationMm > 2 -> 10
                else -> 0
            }
            RideMode.RUN -> when {
                precipitationMm > 6 -> 30
                precipitationMm > 1.5 -> 10
                else -> 0
            }
        }
    }

    private fun windPenalty(windSpeedKmh: Double, rideMode: RideMode): Int {
        return when (rideMode) {
            RideMode.BIKE -> when {
                windSpeedKmh > 30 -> 20
                windSpeedKmh > 20 -> 10
                else -> 0
            }
            RideMode.WALK -> when {
                windSpeedKmh > 35 -> 20
                windSpeedKmh > 25 -> 10
                else -> 0
            }
            RideMode.RUN -> when {
                windSpeedKmh > 30 -> 20
                windSpeedKmh > 22 -> 10
                else -> 0
            }
        }
    }

    private fun precipitationReasonThreshold(rideMode: RideMode): Double {
        return when (rideMode) {
            RideMode.BIKE -> 5.0
            RideMode.WALK -> 7.0
            RideMode.RUN -> 6.0
        }
    }

    private fun windReasonThreshold(rideMode: RideMode): Double {
        return when (rideMode) {
            RideMode.BIKE -> 30.0
            RideMode.WALK -> 35.0
            RideMode.RUN -> 30.0
        }
    }

    private fun temperatureLowReasonThreshold(rideMode: RideMode): Double {
        return when (rideMode) {
            RideMode.BIKE -> 10.0
            RideMode.WALK -> 8.0
            RideMode.RUN -> 10.0
        }
    }

    private fun temperatureHighReasonThreshold(rideMode: RideMode): Double {
        return when (rideMode) {
            RideMode.BIKE -> 30.0
            RideMode.WALK -> 30.0
            RideMode.RUN -> 28.0
        }
    }
}
