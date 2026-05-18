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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.core.extension.formatTemperature
import com.fahimeh.rideready.domain.model.RideMode
import com.fahimeh.rideready.domain.model.TemperatureUnit
import java.time.DayOfWeek
import kotlin.math.roundToInt

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
            .verticalScroll(rememberScrollState())
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

        SettingsSectionCard(title = "Activity") {
            RideModeOption(
                label = "Bike",
                selected = state.settings.selectedRideMode == RideMode.BIKE,
                onClick = { viewModel.updateRideMode(RideMode.BIKE) }
            )

            RideModeOption(
                label = "Walk",
                selected = state.settings.selectedRideMode == RideMode.WALK,
                onClick = { viewModel.updateRideMode(RideMode.WALK) }
            )

            RideModeOption(
                label = "Run",
                selected = state.settings.selectedRideMode == RideMode.RUN,
                onClick = { viewModel.updateRideMode(RideMode.RUN) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsSectionCard(title = "Preferred temperature range") {
            TemperatureStepper(
                label = "Minimum",
                temperatureC = state.settings.preferredMinTemp,
                unit = state.settings.temperatureUnit,
                onDecrease = {
                    viewModel.updatePreferredMinTemp(
                        decrementTemperature(
                            temperatureC = state.settings.preferredMinTemp,
                            unit = state.settings.temperatureUnit
                        )
                    )
                },
                onIncrease = {
                    viewModel.updatePreferredMinTemp(
                        incrementTemperature(
                            temperatureC = state.settings.preferredMinTemp,
                            unit = state.settings.temperatureUnit
                        )
                    )
                },
                decreaseEnabled = state.settings.preferredMinTemp > MIN_PREFERRED_TEMP_C,
                increaseEnabled = state.settings.preferredMinTemp < state.settings.preferredMaxTemp - 1
            )

            TemperatureStepper(
                label = "Maximum",
                temperatureC = state.settings.preferredMaxTemp,
                unit = state.settings.temperatureUnit,
                onDecrease = {
                    viewModel.updatePreferredMaxTemp(
                        decrementTemperature(
                            temperatureC = state.settings.preferredMaxTemp,
                            unit = state.settings.temperatureUnit
                        )
                    )
                },
                onIncrease = {
                    viewModel.updatePreferredMaxTemp(
                        incrementTemperature(
                            temperatureC = state.settings.preferredMaxTemp,
                            unit = state.settings.temperatureUnit
                        )
                    )
                },
                decreaseEnabled = state.settings.preferredMaxTemp > state.settings.preferredMinTemp + 1,
                increaseEnabled = state.settings.preferredMaxTemp < MAX_PREFERRED_TEMP_C
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsSectionCard(title = "Preferred days") {
            Text(
                text = "If no day is selected, all days are considered.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            DaySelectionRow(
                days = WEEKDAY_ROWS.first(),
                selectedDays = state.settings.preferredDays,
                onToggle = { day ->
                    viewModel.updatePreferredDays(state.settings.preferredDays.toggle(day))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            DaySelectionRow(
                days = WEEKDAY_ROWS.last(),
                selectedDays = state.settings.preferredDays,
                onToggle = { day ->
                    viewModel.updatePreferredDays(state.settings.preferredDays.toggle(day))
                }
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

        Spacer(modifier = Modifier.height(16.dp))

        SettingsSectionCard(title = "Available hours") {
            HourStepper(
                label = "Start hour",
                hour = state.settings.availableStartHour,
                onDecrease = {
                    viewModel.updateAvailableStartHour(state.settings.availableStartHour - 1)
                },
                onIncrease = {
                    viewModel.updateAvailableStartHour(state.settings.availableStartHour + 1)
                },
                decreaseEnabled = state.settings.availableStartHour > 0,
                increaseEnabled = state.settings.availableStartHour < state.settings.availableEndHour - 1
            )

            HourStepper(
                label = "End hour",
                hour = state.settings.availableEndHour,
                onDecrease = {
                    viewModel.updateAvailableEndHour(state.settings.availableEndHour - 1)
                },
                onIncrease = {
                    viewModel.updateAvailableEndHour(state.settings.availableEndHour + 1)
                },
                decreaseEnabled = state.settings.availableEndHour > state.settings.availableStartHour + 1,
                increaseEnabled = state.settings.availableEndHour < 23
            )
        }
    }
}

@Composable
private fun DaySelectionRow(
    days: List<DayOfWeek>,
    selectedDays: Set<DayOfWeek>,
    onToggle: (DayOfWeek) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        days.forEach { day ->
            FilterChip(
                selected = day in selectedDays,
                onClick = { onToggle(day) },
                label = {
                    Text(text = day.shortLabel())
                }
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

/**
 * Zeigt eine auswählbare Aktivität.
 */
@Composable
private fun RideModeOption(
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

@Composable
private fun TemperatureStepper(
    label: String,
    temperatureC: Int,
    unit: TemperatureUnit,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    decreaseEnabled: Boolean,
    increaseEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = formatTemperature(temperatureC.toDouble(), unit),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(
                onClick = onDecrease,
                enabled = decreaseEnabled
            ) {
                Text(text = "-")
            }

            Text(
                text = formatTemperature(temperatureC.toDouble(), unit),
                style = MaterialTheme.typography.bodyLarge
            )

            TextButton(
                onClick = onIncrease,
                enabled = increaseEnabled
            ) {
                Text(text = "+")
            }
        }
    }
}

@Composable
private fun HourStepper(
    label: String,
    hour: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    decreaseEnabled: Boolean,
    increaseEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = formatHour(hour),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(
                onClick = onDecrease,
                enabled = decreaseEnabled
            ) {
                Text(text = "-")
            }

            Text(
                text = formatHour(hour),
                style = MaterialTheme.typography.bodyLarge
            )

            TextButton(
                onClick = onIncrease,
                enabled = increaseEnabled
            ) {
                Text(text = "+")
            }
        }
    }
}

private fun formatHour(hour: Int): String = "%02d:00".format(hour)

private fun incrementTemperature(temperatureC: Int, unit: TemperatureUnit): Int {
    return when (unit) {
        TemperatureUnit.CELSIUS -> temperatureC + 1
        TemperatureUnit.FAHRENHEIT -> fahrenheitToCelsius(celsiusToFahrenheit(temperatureC) + 1)
    }
}

private fun decrementTemperature(temperatureC: Int, unit: TemperatureUnit): Int {
    return when (unit) {
        TemperatureUnit.CELSIUS -> temperatureC - 1
        TemperatureUnit.FAHRENHEIT -> fahrenheitToCelsius(celsiusToFahrenheit(temperatureC) - 1)
    }
}

private fun celsiusToFahrenheit(valueC: Int): Int = ((valueC * 9.0 / 5.0) + 32).roundToInt()

private fun fahrenheitToCelsius(valueF: Int): Int = ((valueF - 32) * 5.0 / 9.0).roundToInt()

private const val MIN_PREFERRED_TEMP_C = -20
private const val MAX_PREFERRED_TEMP_C = 45

private val WEEKDAY_ROWS = listOf(
    listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
    listOf(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
)

private fun DayOfWeek.shortLabel(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "Mon"
        DayOfWeek.TUESDAY -> "Tue"
        DayOfWeek.WEDNESDAY -> "Wed"
        DayOfWeek.THURSDAY -> "Thu"
        DayOfWeek.FRIDAY -> "Fri"
        DayOfWeek.SATURDAY -> "Sat"
        DayOfWeek.SUNDAY -> "Sun"
    }
}

private fun Set<DayOfWeek>.toggle(day: DayOfWeek): Set<DayOfWeek> {
    return if (day in this) this - day else this + day
}
