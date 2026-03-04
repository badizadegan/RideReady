package com.fahimeh.rideready.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Repräsentiert eine gespeicherte Stadt in der lokalen Datenbank.
 *
 * Diese Entität wird für Favoritenstädte verwendet.
 */
@Entity(tableName = "cities")
data class CityEntity(

    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val latitude: Double,
    val longitude: Double,

    // Markiert die aktuell ausgewählte Stadt
    val isSelected: Boolean = false
)