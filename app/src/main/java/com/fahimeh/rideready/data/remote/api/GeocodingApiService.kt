package com.fahimeh.rideready.data.remote.api

import com.fahimeh.rideready.data.remote.dto.GeocodingSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API-Schnittstelle für Open-Meteo Geocoding.
 */
interface GeocodingApiService {

    @GET("v1/search")
    suspend fun searchCities(
        @Query("name") query: String
    ): GeocodingSearchResponseDto
}
