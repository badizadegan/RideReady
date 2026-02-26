package com.fahimeh.rideready.presentation.detail

import androidx.lifecycle.ViewModel
import com.fahimeh.rideready.core.forecast.ForecastMemoryStore
import com.fahimeh.rideready.domain.model.ForecastDay
import com.fahimeh.rideready.domain.model.RideScoreResult
import com.fahimeh.rideready.domain.model.TimeWindow
import com.fahimeh.rideready.domain.usecase.FindBestTimeWindowUseCase
import java.time.LocalDate

/**
 * ViewModel für die Detailansicht.
 *
 * Liest den ausgewählten Tag aus einem In-Memory Store.
 */
class DetailViewModel(
    private val memoryStore: ForecastMemoryStore,
    private val findBestTimeWindowUseCase: FindBestTimeWindowUseCase
) : ViewModel() {

    fun getDay(dateString: String): ForecastDay? {
        val date = runCatching { LocalDate.parse(dateString) }.getOrNull()
            ?: return null

        return memoryStore.days.firstOrNull { it.date == date }
    }

    fun getBestWindow(dateString: String): Pair<TimeWindow, RideScoreResult>? {
        val day = getDay(dateString) ?: return null
        return findBestTimeWindowUseCase(day)
    }
}