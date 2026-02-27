package com.fahimeh.rideready.presentation.city

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.fahimeh.rideready.presentation.city.component.CityRow

/**
 * Screen zur Anzeige gespeicherter Städte.
 *
 * Der Screen selbst enthält keine Logik,
 * sondern rendert nur den UiState.
 */
@Composable
fun CityScreen(
    viewModel: CityViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Column {

        // Button zum Hinzufügen einer Teststadt
        Button(onClick = { viewModel.addDummyCity() }) {
            Text("Add city")
        }

        // Liste aller gespeicherten Städte
        LazyColumn {
            items(state.cities) { city ->
                CityRow(
                    city = city,
                    onDelete = { viewModel.deleteCity(city) }
                )
            }
        }
    }
}