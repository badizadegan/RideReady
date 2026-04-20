package com.fahimeh.rideready.domain.repository

import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.domain.model.City
import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {

    fun searchCities(query: String): Flow<AppResult<List<City>>>
}
