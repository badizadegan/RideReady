package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow

/**
 * Beobachtet die aktuell ausgewählte Stadt als Flow.
 */
class ObserveSelectedCityUseCase(
    private val repository: CityRepository
) {
    operator fun invoke(): Flow<City?> = repository.observeSelectedCity()
}