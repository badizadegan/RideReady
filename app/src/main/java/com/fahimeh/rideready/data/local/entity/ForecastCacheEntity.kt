package com.fahimeh.rideready.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Speichert die zuletzt abgerufene Vorhersage für Offline-Zugriff.
 */
@Entity(tableName = "forecast_cache")
data class ForecastCacheEntity(

    @PrimaryKey val cityName: String,
    val rawJson: String,
    val timestamp: Long
)