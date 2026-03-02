package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.CityRepository

/**
 * UseCase zum Löschen einer Stadt.
 */
class DeleteCityUseCase(
    private val repository: CityRepository
) {

    suspend operator fun invoke(city: City) {
        repository.deleteCity(city)
    }
}