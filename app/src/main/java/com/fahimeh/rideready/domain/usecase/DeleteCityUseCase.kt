package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.data.local.entity.CityEntity
import com.fahimeh.rideready.domain.repository.CityRepository

/**
 * UseCase zum Löschen einer Stadt.
 */
class DeleteCityUseCase(
    private val repository: CityRepository
) {

    suspend operator fun invoke(city: CityEntity) {
        repository.deleteCity(city)
    }
}