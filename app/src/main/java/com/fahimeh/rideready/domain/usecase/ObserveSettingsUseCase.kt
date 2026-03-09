package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.AppSettings
import com.fahimeh.rideready.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Beobachtet die Einstellungen der App.
 */
class ObserveSettingsUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<AppSettings> {
        return repository.observeSettings()
    }
}