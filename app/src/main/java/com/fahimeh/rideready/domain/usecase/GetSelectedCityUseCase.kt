package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.CityRepository

class GetSelectedCityUseCase(
    private val repository: CityRepository
) {
    suspend operator fun invoke(): City? = repository.getSelectedCity()
}