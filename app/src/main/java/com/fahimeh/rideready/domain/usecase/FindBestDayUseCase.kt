package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideScoreResult

/**
 * Verantwortlich für die Auswahl des besten Tages.
 *
 * Dieser UseCase nutzt die Bewertungslogik (CalculateRideScoreUseCase)
 * und wählt den Tag mit dem höchsten Score aus.
 */
class FindBestDayUseCase(
    private val calculateRideScoreUseCase: CalculateRideScoreUseCase
) {

    operator fun invoke(days: List<ForecastDay>
    ): Pair<ForecastDay, RideScoreResult>? {

        // Falls keine Daten vorhanden sind
        if (days.isEmpty()) return null

        return days
            // Jeder Tag wird mit einem Score bewertet
            .map { day -> day to calculateRideScoreUseCase(day) }

            // Auswahl des Tages mit dem höchsten Score
            .maxByOrNull { (_, score) -> score.score }
    }
}