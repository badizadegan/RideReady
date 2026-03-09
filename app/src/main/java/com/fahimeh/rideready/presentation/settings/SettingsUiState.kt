package com.fahimeh.rideready.presentation.settings

import com.fahimeh.rideready.domain.model.AppSettings

/**
 * Zustand für den Settings-Screen.
 *
 * Die UI zeigt nur die aktuellen Werte an.
 */
data class SettingsUiState(
    val settings: AppSettings = AppSettings()
)