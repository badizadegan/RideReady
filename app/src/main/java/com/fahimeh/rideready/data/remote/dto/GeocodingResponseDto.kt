package com.fahimeh.rideready.data.remote.dto

import com.squareup.moshi.Json

data class GeocodingSearchResponseDto(
    @Json(name = "results")
    val results: List<GeocodingResponseDto>? = null
)

data class GeocodingResponseDto(
    @Json(name = "name")
    val name: String,

    @Json(name = "latitude")
    val latitude: Double,

    @Json(name = "longitude")
    val longitude: Double,

    @Json(name = "country")
    val country: String? = null
)
