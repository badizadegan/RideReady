package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow

/**
 * UseCase zum Beobachten gespeicherter Städte.
 *
 * Der ViewModel ruft nur diesen UseCase auf,
 * nicht direkt das Repository.
 */
class GetSavedCitiesUseCase(
    private val repository: CityRepository
) {

    operator fun invoke(): Flow<List<City>> {
        return repository.observeCities()
    }
}