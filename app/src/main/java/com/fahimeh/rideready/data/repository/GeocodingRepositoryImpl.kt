package com.fahimeh.rideready.data.repository

import com.fahimeh.rideready.core.error.AppError
import com.fahimeh.rideready.core.result.AppResult
import com.fahimeh.rideready.data.remote.api.GeocodingApiService
import com.fahimeh.rideready.domain.model.City
import com.fahimeh.rideready.domain.repository.GeocodingRepository
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GeocodingRepositoryImpl(
    private val apiService: GeocodingApiService
) : GeocodingRepository {

    override fun searchCities(query: String): Flow<AppResult<List<City>>> = flow {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isBlank()) {
            emit(AppResult.Success(emptyList()))
            return@flow
        }

        emit(AppResult.Loading)

        try {
            val response = apiService.searchCities(trimmedQuery)
            val cities = response.results.orEmpty().map { result ->
                City(
                    name = buildCityName(result.name, result.country),
                    latitude = result.latitude,
                    longitude = result.longitude
                )
            }

            emit(AppResult.Success(cities))
        } catch (e: IOException) {
            emit(AppResult.Error(AppError.Network))
        } catch (e: Exception) {
            emit(AppResult.Error(AppError.Unknown(e.message)))
        }
    }

    private fun buildCityName(name: String, country: String?): String {
        return if (country.isNullOrBlank()) {
            name
        } else {
            "$name, $country"
        }
    }
}
