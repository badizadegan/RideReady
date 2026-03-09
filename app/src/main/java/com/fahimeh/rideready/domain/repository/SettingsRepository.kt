package com.fahimeh.rideready.domain.repository

import com.fahimeh.rideready.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

/**
 * Repository für App-Einstellungen.
 *
 * Die Domain kennt nur dieses Interface
 * und nicht DataStore direkt.
 */
interface SettingsRepository {

    /**
     * Gibt die aktuellen Einstellungen als Flow zurück.
     * So kann die UI automatisch auf Änderungen reagieren.
     */
    fun observeSettings(): Flow<AppSettings>

    // Speichert die Temperatur-Einheit.
    suspend fun updateTemperatureUnit(unit: String)

    // Speichert die Länge des Zeitfensters.
    suspend fun updateTimeWindowHours(hours: Int)
}