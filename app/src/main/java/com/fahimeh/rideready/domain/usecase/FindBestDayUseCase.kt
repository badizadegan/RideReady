package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.AppSettings
import com.fahimeh.rideready.domain.model.RideMode
import com.fahimeh.rideready.domain.model.RideScoreResult
import java.time.DayOfWeek

/**
 * Verantwortlich für die Auswahl des besten Tages.
 *
 * Dieser UseCase nutzt die Bewertungslogik (CalculateRideScoreUseCase)
 * und wählt den Tag mit dem höchsten Score aus.
 */
class FindBestDayUseCase(
    private val calculateRideScoreUseCase: CalculateRideScoreUseCase
) {

    operator fun invoke(
        days: List<ForecastDay>,
        rideMode: RideMode = RideMode.BIKE,
        preferredMinTemp: Int = AppSettings.DEFAULT_PREFERRED_MIN_TEMP,
        preferredMaxTemp: Int = AppSettings.DEFAULT_PREFERRED_MAX_TEMP,
        preferredDays: Set<DayOfWeek> = emptySet()
    ): Pair<ForecastDay, RideScoreResult>? {

        // Falls keine Daten vorhanden sind
        if (days.isEmpty()) return null

        val filteredDays = if (preferredDays.isEmpty()) {
            days
        } else {
            days.filter { it.date.dayOfWeek in preferredDays }
        }

        if (filteredDays.isEmpty()) return null

        return filteredDays
            // Jeder Tag wird mit einem Score bewertet
            .map { day ->
                day to calculateRideScoreUseCase(
                    day = day,
                    rideMode = rideMode,
                    preferredMinTemp = preferredMinTemp,
                    preferredMaxTemp = preferredMaxTemp
                )
            }

            // Auswahl des Tages mit dem höchsten Score
            .maxByOrNull { (_, score) -> score.score }
    }
}
