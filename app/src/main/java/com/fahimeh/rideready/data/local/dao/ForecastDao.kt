package com.fahimeh.rideready.data.local.dao

import androidx.room.*
import com.fahimeh.rideready.data.local.entity.ForecastCacheEntity

/**
 * DAO für gespeicherte Forecast-Daten.
 */
@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast_cache WHERE cityName = :city LIMIT 1")
    suspend fun getForecastForCity(city: String): ForecastCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: ForecastCacheEntity)
}