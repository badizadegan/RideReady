package com.fahimeh.rideready.presentation.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Bottom-Navigation-Leiste der App.
 *
 * Reagiert auf den aktuellen Navigationszustand
 * und hebt den aktiven Tab hervor.
 */
@Composable
fun BottomBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cities,
        BottomNavItem.Settings
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Routes.HOME)
                        launchSingleTop = true
                    }
                },
                icon = { androidx.compose.material3.Icon(item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}