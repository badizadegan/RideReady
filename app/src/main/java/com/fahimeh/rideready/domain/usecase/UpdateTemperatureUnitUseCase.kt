package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.repository.SettingsRepository

/**
 * Speichert die neue Temperatur-Einheit.
 */
class UpdateTemperatureUnitUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(unit: String) {
        repository.updateTemperatureUnit(unit)
    }
}