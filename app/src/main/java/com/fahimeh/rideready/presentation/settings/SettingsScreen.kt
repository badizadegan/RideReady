package com.fahimeh.rideready.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.domain.model.TemperatureUnit

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

        SettingsSectionCard(title = "Temperature unit") {
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsSectionCard(title = "Time window length") {
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
}

@Composable
private fun SettingsSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = "$hours h",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}