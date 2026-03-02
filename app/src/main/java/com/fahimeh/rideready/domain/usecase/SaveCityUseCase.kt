package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.CityRepository

/**
 * UseCase zum Speichern einer Stadt.
 */
class SaveCityUseCase(
    private val repository: CityRepository
) {

    suspend operator fun invoke(city: City) {
        repository.saveCity(city)
    }
}