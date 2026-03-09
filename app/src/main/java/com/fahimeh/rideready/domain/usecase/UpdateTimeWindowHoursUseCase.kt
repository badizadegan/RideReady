package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.repository.SettingsRepository

/**
 * Speichert die neue Länge des Zeitfensters.
 */
class UpdateTimeWindowHoursUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(hours: Int) {
        repository.updateTimeWindowHours(hours)
    }
}