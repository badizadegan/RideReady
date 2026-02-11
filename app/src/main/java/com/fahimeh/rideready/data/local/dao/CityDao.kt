package com.fahimeh.rideready.data.local.dao

import androidx.room.*
import com.fahimeh.rideready.data.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO für CRUD-Operationen auf Städten.
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    fun getAllCities(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Delete
    suspend fun deleteCity(city: CityEntity)
}