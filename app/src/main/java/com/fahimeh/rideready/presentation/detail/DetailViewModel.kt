package com.fahimeh.rideready.presentation.detail

import androidx.lifecycle.ViewModel
import com.fahimeh.rideready.core.forecast.ForecastMemoryStore
import com.fahimeh.rideready.domain.model.ForecastDay
import java.time.LocalDate

/**
 * ViewModel für die Detailansicht.
 *
 * Liest den ausgewählten Tag aus einem In-Memory Store.
 */
class DetailViewModel(
    private val memoryStore: ForecastMemoryStore
) : ViewModel() {

    fun getDay(dateString: String): ForecastDay? {
        val date = runCatching { LocalDate.parse(dateString) }.getOrNull()
            ?: return null

        return memoryStore.days.firstOrNull { it.date == date }
    }
}