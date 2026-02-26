package com.fahimeh.rideready.data.repository

import com.fahimeh.rideready.data.local.dao.CityDao
import com.fahimeh.rideready.data.local.entity.CityEntity
import com.fahimeh.rideready.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow

/**
 * Room-basierte Implementierung des CityRepository.
 */
class CityRepositoryImpl(
    private val dao: CityDao
) : CityRepository {

    override fun observeCities(): Flow<List<CityEntity>> {
        // Direkte Weitergabe des DAO-Flows
        return dao.getAllCities()
    }

    override suspend fun saveCity(city: CityEntity) {
        dao.insertCity(city)
    }

    override suspend fun deleteCity(city: CityEntity) {
        dao.deleteCity(city)
    }
}