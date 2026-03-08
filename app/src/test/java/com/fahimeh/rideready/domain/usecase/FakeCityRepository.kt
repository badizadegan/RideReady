package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Fake-Repository für Unit-Tests.
 *
 * Ersetzt Room/DB, damit UseCases isoliert getestet werden können.
 */
class FakeCityRepository : CityRepository {

    private val citiesFlow = MutableStateFlow<List<City>>(emptyList())

    override fun observeCities(): Flow<List<City>> = citiesFlow

    override suspend fun saveCity(city: City) {
        // Fügt eine Stadt in die Liste hinzu
        citiesFlow.value = citiesFlow.value + city
    }

    override suspend fun deleteCity(city: City) {
        // Löscht anhand der ID
        citiesFlow.value = citiesFlow.value.filterNot { it.id == city.id }
    }

    override suspend fun selectCity(cityId: Long) {
        // Markiert genau eine Stadt als ausgewählt
        citiesFlow.value = citiesFlow.value.map { it.copy(isSelected = it.id == cityId) }
    }

    override suspend fun getSelectedCity(): City? {
        return citiesFlow.value.firstOrNull { it.isSelected }
    }

    override fun observeSelectedCity(): Flow<City?> {
        return citiesFlow.map { list -> list.firstOrNull { it.isSelected } }
    }
}