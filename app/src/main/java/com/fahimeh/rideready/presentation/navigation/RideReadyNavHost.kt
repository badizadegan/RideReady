package com.fahimeh.rideready.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.fahimeh.rideready.presentation.city.CityScreen
import com.fahimeh.rideready.presentation.city.CityViewModel
import com.fahimeh.rideready.presentation.detail.DetailScreen
import com.fahimeh.rideready.presentation.detail.DetailViewModel
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

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry.value?.destination

    val showBottomBar =
        destination?.hasRoute(HomeRoute::class) == true ||
                destination?.hasRoute(CitiesRoute::class) == true ||
                destination?.hasRoute(SettingsRoute::class) == true

    Scaffold(
        topBar = {
            RideReadyTopBar(navController = navController)
        },
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController = navController)
            }
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

            composable<DetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<DetailRoute>()
                val vm: DetailViewModel = koinViewModel()

                DetailScreen(
                    date = route.date,
                    viewModel = vm
                )
            }

            composable<CitiesRoute> {
                val vm: CityViewModel = koinViewModel()

                CityScreen(
                    viewModel = vm
                )
            }

            composable<SettingsRoute> {
                SettingsScreen()
            }
        }
    }
}