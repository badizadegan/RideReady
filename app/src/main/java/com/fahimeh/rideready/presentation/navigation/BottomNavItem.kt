package com.fahimeh.rideready.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Modell für Bottom-Navigation-Einträge.
 *
 * Enthält alle Informationen, die für die Darstellung
 * und Navigation eines Tabs benötigt werden.
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {

    object Home : BottomNavItem(
        route = Routes.HOME,
        label = "Home",
        icon = Icons.Filled.Home
    )

    object Cities : BottomNavItem(
        route = Routes.CITIES,
        label = "Cities",
        icon = Icons.Filled.LocationCity
    )

    object Settings : BottomNavItem(
        route = Routes.SETTINGS,
        label = "Settings",
        icon = Icons.Filled.Settings
    )
}