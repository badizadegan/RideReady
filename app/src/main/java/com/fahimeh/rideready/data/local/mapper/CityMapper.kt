package com.fahimeh.rideready.data.local.mapper

import com.fahimeh.rideready.data.local.entity.CityEntity
import com.fahimeh.rideready.domain.model.City

/**
 * Mapper zwischen Room-Entity und Domain-Modell.
 *
 * Trennt Datenbank-Struktur von der Domain-Schicht.
 */
fun CityEntity.toDomain(): City {
    return City(
        id = id ,
        name = name,
        latitude = latitude,
        longitude = longitude,
        isSelected = isSelected
    )
}

fun City.toEntity(): CityEntity {
    return CityEntity(
        id = id ,
        name = name,
        latitude = latitude,
        longitude = longitude,
        isSelected = isSelected
    )
}