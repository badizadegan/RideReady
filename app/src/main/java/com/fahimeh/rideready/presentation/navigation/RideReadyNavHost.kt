package com.fahimeh.rideready.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fahimeh.rideready.presentation.city.CityScreen
import com.fahimeh.rideready.presentation.detail.DetailScreen
import com.fahimeh.rideready.presentation.home.HomeScreen
import com.fahimeh.rideready.presentation.settings.SettingsScreen

/**
 * Zentrale Navigations-Komponente der App.
 *
 * Hier werden alle Screens miteinander verbunden.
 */
@Composable
fun RideReadyNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Routes.HOME) {
                HomeScreen(
                    onNavigateToDetail = {
                        navController.navigate(Routes.DETAIL)
                    },
                    onNavigateToCities = {
                        navController.navigate(Routes.CITIES)
                    },
                    onNavigateToSettings = {
                        navController.navigate(Routes.SETTINGS)
                    }
                )
            }

            composable(Routes.DETAIL) {
                DetailScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.CITIES) {
                CityScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.SETTINGS) {
                SettingsScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}