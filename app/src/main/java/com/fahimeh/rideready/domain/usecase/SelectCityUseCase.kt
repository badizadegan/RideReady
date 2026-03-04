package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.repository.CityRepository

class SelectCityUseCase(
    private val repository: CityRepository
) {
    suspend operator fun invoke(cityId: Long) {
        repository.selectCity(cityId)
    }
}