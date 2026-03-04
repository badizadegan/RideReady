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
    suspend fun insertCity(city: CityEntity): Long

    @Delete
    suspend fun deleteCity(city: CityEntity)

    // selected city
    // Liefert die aktuell ausgewählte Stadt
    @Query("SELECT * FROM cities WHERE isSelected = 1 LIMIT 1")
    suspend fun getSelectedCity(): CityEntity?

    // Setzt alle Städte auf nicht ausgewählt
    @Query("UPDATE cities SET isSelected = 0")
    suspend fun clearSelection()

    // Markiert eine Stadt als ausgewählt
    @Query("UPDATE cities SET isSelected = 1 WHERE id = :cityId")
    suspend fun setSelected(cityId: Long)

    // Hilfsmethode
    // Zuerst wird die Auswahl aller Städte gelöscht,
    // danach wird die gewünschte Stadt als aktiv markiert.
    @Transaction
    suspend fun selectCity(cityId: Long) {
        clearSelection()
        setSelected(cityId)
    }
}