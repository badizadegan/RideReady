package com.fahimeh.rideready.core.forecast

import com.fahimeh.rideready.domain.model.ForecastDay

/**
 * Einfacher In-Memory Speicher für die aktuelle Forecast-Liste.
 */
class ForecastMemoryStore {

    // Der Setter ist private, sodass die Liste
    // nur innerhalb dieser Klasse verändert werden kann.
    var days: List<ForecastDay> = emptyList()
        private set

    fun update(days: List<ForecastDay>) {
        this.days = days
    }
}