package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.data.local.entity.CityEntity
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

    operator fun invoke(): Flow<List<CityEntity>> {
        return repository.observeCities()
    }
}