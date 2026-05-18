package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.AppSettings
import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideMode
import com.fahimeh.rideready.domain.model.RideScoreResult

/**
 * Berechnet einen einfachen Score (0..100) für einen Tag.
 */
class CalculateRideScoreUseCase {
    operator fun invoke(
        day: ForecastDay,
        rideMode: RideMode = RideMode.BIKE,
        preferredMinTemp: Int = AppSettings.DEFAULT_PREFERRED_MIN_TEMP,
        preferredMaxTemp: Int = AppSettings.DEFAULT_PREFERRED_MAX_TEMP
    ): RideScoreResult {
        var score = 100

        val preferredRange = normalizePreferredRange(preferredMinTemp, preferredMaxTemp)
        val avgTemp = (day.minTempC + day.maxTempC) / 2
        score -= temperaturePenalty(avgTemp, rideMode, preferredRange)
        score -= precipitationPenalty(day.precipitationMm, rideMode)
        score -= windPenalty(day.windSpeedKmh, rideMode)

        score = score.coerceIn(0, 100)

        val reason = when {
            day.precipitationMm > precipitationReasonThreshold(rideMode) -> "High precipitation"
            day.windSpeedKmh > windReasonThreshold(rideMode) -> "Strong wind"
            avgTemp < temperatureLowReasonThreshold(rideMode, preferredRange) -> "Too cold"
            avgTemp > temperatureHighReasonThreshold(rideMode, preferredRange) -> "Too hot"
            else -> "Good overall conditions"
        }

        return RideScoreResult(
            score = score,
            reason = reason
        )
    }

    private fun temperaturePenalty(
        avgTemp: Double,
        rideMode: RideMode,
        preferredRange: ClosedFloatingPointRange<Double>
    ): Int {
        val tolerableRange = tolerableTemperatureRange(rideMode, preferredRange)

        return when (rideMode) {
            RideMode.BIKE -> when {
                avgTemp !in tolerableRange -> 30
                avgTemp !in preferredRange -> 15
                else -> 0
            }
            RideMode.WALK -> when {
                avgTemp !in tolerableRange -> 25
                avgTemp !in preferredRange -> 10
                else -> 0
            }
            RideMode.RUN -> when {
                avgTemp !in tolerableRange -> 30
                avgTemp !in preferredRange -> 20
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

    private fun temperatureLowReasonThreshold(
        rideMode: RideMode,
        preferredRange: ClosedFloatingPointRange<Double>
    ): Double {
        return tolerableTemperatureRange(rideMode, preferredRange).start
    }

    private fun temperatureHighReasonThreshold(
        rideMode: RideMode,
        preferredRange: ClosedFloatingPointRange<Double>
    ): Double {
        return tolerableTemperatureRange(rideMode, preferredRange).endInclusive
    }

    private fun normalizePreferredRange(
        preferredMinTemp: Int,
        preferredMaxTemp: Int
    ): ClosedFloatingPointRange<Double> {
        val min = minOf(preferredMinTemp, preferredMaxTemp).toDouble()
        val max = maxOf(preferredMinTemp, preferredMaxTemp).toDouble()
        return min..max
    }

    private fun tolerableTemperatureRange(
        rideMode: RideMode,
        preferredRange: ClosedFloatingPointRange<Double>
    ): ClosedFloatingPointRange<Double> {
        return when (rideMode) {
            RideMode.BIKE -> (preferredRange.start - 5.0)..(preferredRange.endInclusive + 5.0)
            RideMode.WALK -> (preferredRange.start - 4.0)..(preferredRange.endInclusive + 5.0)
            RideMode.RUN -> (preferredRange.start - 3.0)..(preferredRange.endInclusive + 4.0)
        }
    }
}
