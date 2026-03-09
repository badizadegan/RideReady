package com.fahimeh.rideready.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.domain.model.TemperatureUnit
import org.koin.androidx.compose.koinViewModel

/**
 * Einfacher Settings-Screen.
 *
 * Hier kann der Nutzer die wichtigsten Einstellungen ändern.
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Temperature unit",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )

        TemperatureOption(
            label = "Celsius",
            selected = state.settings.temperatureUnit == TemperatureUnit.CELSIUS,
            onClick = { viewModel.updateTemperatureUnit(TemperatureUnit.CELSIUS) }
        )

        TemperatureOption(
            label = "Fahrenheit",
            selected = state.settings.temperatureUnit == TemperatureUnit.FAHRENHEIT,
            onClick = { viewModel.updateTemperatureUnit(TemperatureUnit.FAHRENHEIT) }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = "Time window length",
            style = MaterialTheme.typography.titleMedium
        )

        TimeWindowOption(
            hours = 1,
            selected = state.settings.timeWindowHours == 1,
            onClick = { viewModel.updateTimeWindow(1) }
        )

        TimeWindowOption(
            hours = 2,
            selected = state.settings.timeWindowHours == 2,
            onClick = { viewModel.updateTimeWindow(2) }
        )

        TimeWindowOption(
            hours = 3,
            selected = state.settings.timeWindowHours == 3,
            onClick = { viewModel.updateTimeWindow(3) }
        )
    }
}

/**
 * Zeigt eine auswählbare Temperatur-Einheit.
 */
@Composable
private fun TemperatureOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = label,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

/**
 * Zeigt eine auswählbare Länge für das Zeitfenster.
 */
@Composable
private fun TimeWindowOption(
    hours: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = "$hours h",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}