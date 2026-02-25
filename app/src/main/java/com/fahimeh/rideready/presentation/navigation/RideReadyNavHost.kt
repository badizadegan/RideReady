package com.fahimeh.rideready.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fahimeh.rideready.presentation.city.CityScreen
import com.fahimeh.rideready.presentation.detail.DetailScreen
import com.fahimeh.rideready.presentation.home.HomeScreen
import com.fahimeh.rideready.presentation.home.HomeViewModel
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
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable<HomeRoute> {

                val vm: HomeViewModel = koinViewModel()

                HomeScreen(
                    viewModel = vm,
                    onNavigateToDetail = { date ->
                        navController.navigate(
                            DetailRoute(date = date)
                        )
                    },
                    onNavigateToCities = {
                        navController.navigate(CitiesRoute)
                    },
                    onNavigateToSettings = {
                        navController.navigate(SettingsRoute)
                    }
                )
            }

            composable<DetailRoute> {
                DetailScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable<CitiesRoute> {
                CityScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable<SettingsRoute> {
                SettingsScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}