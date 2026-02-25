package com.fahimeh.rideready.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Zentrale Definition aller Navigationsrouten der App.
 */
@Serializable
object HomeRoute

// Route mit Argumenten = data class
@Serializable
data class DetailRoute(
    val date: String
)

@Serializable
object CitiesRoute

@Serializable
object SettingsRoute