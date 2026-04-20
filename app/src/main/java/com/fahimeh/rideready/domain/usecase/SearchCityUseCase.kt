package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.GeocodingRepository
import kotlinx.coroutines.flow.Flow

class SearchCityUseCase(
    private val repository: GeocodingRepository
) {

    operator fun invoke(query: String): Flow<AppResult<List<City>>> {
        return repository.searchCities(query)
    }
}
