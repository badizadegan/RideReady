package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.HourlyForecast
import com.fahimeh.rideready.domain.model.RideScoreResult
import com.fahimeh.rideready.domain.model.TimeWindow
import java.time.Duration

/**
 * Berechnet das beste Zeitfenster innerhalb eines Tages.
 *
 * Bewertet Regen und Wind über ein Sliding Window.
 */
class FindBestTimeWindowUseCase {

    operator fun invoke(
        day: ForecastDay,
        windowHours: Int = 2
    ): Pair<TimeWindow, RideScoreResult>? {

        val hours = day.hourly
        if (hours.size < windowHours) return null

        var best: Pair<TimeWindow, RideScoreResult>? = null

        // Wir verschieben das Fenster stundenweise durch den Tag
        for (startIndex in 0..(hours.size - windowHours)) {

            val slice = hours.subList(startIndex, startIndex + windowHours)

            // Wenn die Stunden nicht zusammenhängend sind, überspringen wir dieses Fenster
            if (!isContinuous(slice)) continue

            val score = calculateScore(slice)

            val window = TimeWindow(
                start = slice.first().time,
                end = slice.last().time.plusHours(1)
            )

            val result = RideScoreResult(
                score = score,
                reason = buildReason(slice)
            )

            if (best == null || score > best.second.score) {
                best = window to result
            }
        }

        return best
    }

    private fun calculateScore(slice: List<HourlyForecast>): Int {
        var score = 100

        val totalRain = slice.sumOf { it.precipitationMm }
        val avgWind = slice.map { it.windSpeedKmh }.average()

        // Wenn es stark regnet, reduzieren wir den Score deutlich
        if (totalRain > 2) score -= 40
        // Bei leichtem Regen nur leichte Abwertung
        else if (totalRain > 0.5) score -= 15

        // Starker Wind ist ungünstig für Outdoor-Aktivitäten
        if (avgWind > 30) score -= 25
        // Mäßiger Wind wirkt sich etwas negativ aus
        else if (avgWind > 20) score -= 12

        return score.coerceIn(0, 100)
    }

    private fun buildReason(slice: List<HourlyForecast>): String {
        val totalRain = slice.sumOf { it.precipitationMm }

        // Wenn kaum Regen vorhanden ist, bevorzugen wir dieses Fenster
        return if (totalRain <= 0.5)
            "Low rain conditions"
        else
        // Andernfalls ist es einfach das beste verfügbare Zeitfenster
            "Best available time window"
    }

    private fun isContinuous(slice: List<HourlyForecast>): Boolean {
        for (i in 0 until slice.size - 1) {
            val diff = Duration.between(slice[i].time, slice[i + 1].time)

            // Prüft, ob die Stunden wirklich aufeinander folgen
            if (diff.toHours() != 1L) return false
        }
        return true
    }
}