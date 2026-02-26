package com.fahimeh.rideready.domain.repository

import com.fahimeh.rideready.data.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository-Interface für gespeicherte Städte.
 *
 * Die Domain kennt nur dieses Interface,
 * nicht die konkrete Room-Implementierung.
 */
interface CityRepository {

    /**
     * Gibt alle gespeicherten Städte als Flow zurück.
     * Flow wird verwendet, damit UI automatisch reagiert.
     */
    fun observeCities(): Flow<List<CityEntity>>

    suspend fun saveCity(city: CityEntity)

    suspend fun deleteCity(city: CityEntity)
}