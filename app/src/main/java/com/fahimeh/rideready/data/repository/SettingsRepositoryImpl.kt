package com.fahimeh.rideready.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fahimeh.rideready.domain.model.AppSettings
import com.fahimeh.rideready.domain.model.RideMode
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
        private val KEY_RIDE_MODE = stringPreferencesKey("ride_mode")
        private val KEY_AVAILABLE_START_HOUR = intPreferencesKey("available_start_hour")
        private val KEY_AVAILABLE_END_HOUR = intPreferencesKey("available_end_hour")
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
                timeWindowHours = prefs[KEY_TIME_WINDOW_HOURS] ?: 2,
                selectedRideMode = when (prefs[KEY_RIDE_MODE]) {
                    RideMode.WALK.name -> RideMode.WALK
                    RideMode.RUN.name -> RideMode.RUN
                    else -> RideMode.BIKE
                },
                availableStartHour = prefs[KEY_AVAILABLE_START_HOUR]
                    ?: AppSettings.DEFAULT_AVAILABLE_START_HOUR,
                availableEndHour = prefs[KEY_AVAILABLE_END_HOUR]
                    ?: AppSettings.DEFAULT_AVAILABLE_END_HOUR
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

    override suspend fun updateRideMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_RIDE_MODE] = mode
        }
    }

    override suspend fun updateAvailableStartHour(hour: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_AVAILABLE_START_HOUR] = hour
        }
    }

    override suspend fun updateAvailableEndHour(hour: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_AVAILABLE_END_HOUR] = hour
        }
    }
}
