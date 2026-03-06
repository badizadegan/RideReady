package com.fahimeh.rideready.domain.repository

import com.fahimeh.rideready.domain.model.City
import kotlinx.coroutines.flow.Flow

/**
 * Repository-Interface für gespeicherte Städte.
 *
 * Die Domain kennt nur das City-Modell,
 * nicht die Room-Entity.
 */
interface CityRepository {

    /**
     * Beobachtet alle gespeicherten Städte.
     */
    fun observeCities(): Flow<List<City>>

    fun observeSelectedCity(): Flow<City?>

    suspend fun saveCity(city: City)

    suspend fun deleteCity(city: City)

    suspend fun selectCity(cityId: Long)
    suspend fun getSelectedCity(): City?
}