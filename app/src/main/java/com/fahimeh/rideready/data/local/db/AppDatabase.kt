package com.fahimeh.rideready.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fahimeh.rideready.data.local.dao.CityDao
import com.fahimeh.rideready.data.local.dao.ForecastDao
import com.fahimeh.rideready.data.local.entity.CityEntity
import com.fahimeh.rideready.data.local.entity.ForecastCacheEntity

/**
 * Zentrale Room-Datenbank der App.
 *
 * Enthält alle Entitäten und DAOs.
 */
@Database(
    entities = [CityEntity::class, ForecastCacheEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun forecastDao(): ForecastDao
}