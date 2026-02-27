package com.fahimeh.rideready.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Zentrale Top-App-Bar der Anwendung.
 *
 * Zeigt je nach Route Titel und optional eine Zurück-Navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideReadyTopBar(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry.value?.destination

    val showBackButton = destination?.hasRoute(DetailRoute::class) == true

    val title = when {
        destination?.hasRoute(HomeRoute::class) == true -> "Home"
        destination?.hasRoute(CitiesRoute::class) == true -> "Cities"
        destination?.hasRoute(SettingsRoute::class) == true -> "Settings"
        destination?.hasRoute(DetailRoute::class) == true -> "Detail"
        else -> ""
    }

    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}