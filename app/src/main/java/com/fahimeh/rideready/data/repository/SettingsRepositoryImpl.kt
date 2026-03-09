package com.fahimeh.rideready.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fahimeh.rideready.domain.model.AppSettings
import com.fahimeh.rideready.domain.model.TemperatureUnit
import com.fahimeh.rideready.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Erstellt eine DataStore-Instanz für die App-Einstellungen
private val Context.dataStore by preferencesDataStore(name = "app_settings")

/**
 * Implementierung des SettingsRepository mit DataStore.
 *
 * Hier werden einfache Werte lokal gespeichert.
 */
class SettingsRepositoryImpl(
    private val context: Context
) : SettingsRepository {

    companion object {
        /**
         * Diese Keys sind feste Konstanten.
         * Sie gehören zur Klasse und nicht zu einer einzelnen Instanz.
         *
         * Schlüssel für die gespeicherten Werte
         */
        private val KEY_TEMPERATURE_UNIT = stringPreferencesKey("temperature_unit")
        private val KEY_TIME_WINDOW_HOURS = intPreferencesKey("time_window_hours")
    }

    override fun observeSettings(): Flow<AppSettings> {
        return context.dataStore.data.map { prefs ->
            AppSettings(
                // Liest die Temperatur-Einheit aus DataStore
                temperatureUnit = when (prefs[KEY_TEMPERATURE_UNIT]) {
                    "FAHRENHEIT" -> TemperatureUnit.FAHRENHEIT
                    else -> TemperatureUnit.CELSIUS
                },

                // Falls kein Wert gespeichert ist, wird der Standardwert genutzt
                timeWindowHours = prefs[KEY_TIME_WINDOW_HOURS] ?: 2
            )
        }
    }

    override suspend fun updateTemperatureUnit(unit: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TEMPERATURE_UNIT] = unit
        }
    }

    override suspend fun updateTimeWindowHours(hours: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TIME_WINDOW_HOURS] = hours
        }
    }
}