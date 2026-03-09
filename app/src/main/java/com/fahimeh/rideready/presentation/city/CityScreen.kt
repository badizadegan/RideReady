package com.fahimeh.rideready.presentation.city

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.presentation.city.component.CityRow

/**
 * Screen für gespeicherte Städte.
 *
 * Der Nutzer kann Städte sehen, auswählen und löschen.
 * Neue Städte werden über den FloatingActionButton hinzugefügt.
 */
@Composable
fun CityScreen(
    viewModel: CityViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.cities.isEmpty()) {
            Text(
                text = "No cities saved yet",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.cities) { city ->
                    CityRow(
                        city = city,
                        onSelect = { viewModel.selectCity(city.id) },
                        onDelete = { viewModel.deleteCity(city) }
                    )
                }
            }
        }

        // Button zum Hinzufügen einer Stadt
        FloatingActionButton(
            onClick = { viewModel.addDummyCity() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add city"
            )
        }
    }
}