package com.fahimeh.rideready.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.fahimeh.rideready.R

/**
 * Modell für Bottom-Navigation-Einträge.
 *
 * Enthält alle Informationen, die für die Darstellung
 * und Navigation eines Tabs benötigt werden.
 */
sealed class BottomNavItem(
    val route: String,
    val labelResId: Int,
    val icon: ImageVector
) {

    object Home : BottomNavItem(
        route = Routes.HOME,
        labelResId = R.string.home_tab,
        icon = Icons.Filled.Home
    )

    object Cities : BottomNavItem(
        route = Routes.CITIES,
        labelResId = R.string.cities_tab,
        icon = Icons.Filled.LocationCity
    )

    object Settings : BottomNavItem(
        route = Routes.SETTINGS,
        labelResId = R.string.settings_tab,
        icon = Icons.Filled.Settings
    )
}