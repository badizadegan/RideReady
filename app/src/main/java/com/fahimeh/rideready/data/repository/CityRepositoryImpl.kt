package com.fahimeh.rideready.data.repository

import com.fahimeh.rideready.data.local.dao.CityDao
import com.fahimeh.rideready.data.local.mapper.toDomain
import com.fahimeh.rideready.data.local.mapper.toEntity
import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Room-basierte Implementierung des CityRepository.
 *
 * Hier wird zwischen Entity und Domain-Modell gemappt.
 */
class CityRepositoryImpl(
    private val dao: CityDao
) : CityRepository {

    override fun observeCities(): Flow<List<City>> {
        // Entity → Domain umwandeln
        return dao.getAllCities().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun saveCity(city: City) {
        // Domain → Entity umwandeln
        dao.insertCity(city.toEntity())
    }

    override suspend fun deleteCity(city: City) {
        dao.deleteCity(city.toEntity())
    }

    override suspend fun selectCity(cityId: Long) {
        dao.selectCity(cityId)
    }

    override suspend fun getSelectedCity(): City? {
        return dao.getSelectedCity()?.toDomain()
    }
}