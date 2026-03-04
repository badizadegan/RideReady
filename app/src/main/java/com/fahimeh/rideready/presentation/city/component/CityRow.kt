package com.fahimeh.rideready.presentation.city.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahimeh.rideready.domain.model.City

/**
 * Einzelne Zeile für eine gespeicherte Stadt.
 */
@Composable
fun CityRow(
    city: City,
    onDelete: () -> Unit,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Klick wählt die Stadt aus
            .clickable { onSelect() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = city.name)

        // Zeigt an welche Stadt aktiv ist
        if (city.isSelected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Selected"
            )
        }

        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}