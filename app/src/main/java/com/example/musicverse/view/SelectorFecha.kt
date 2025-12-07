package com.example.musicverse.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorFecha(
    formato: String,
    contexto: String,
    fechaSeleccionada: String,
    onFechaSeleccionada: (String) -> Unit
) {
    var mostrarPicker by remember { mutableStateOf(false) }
    // Campo visible
    OutlinedTextField(
        value = fechaSeleccionada,
        onValueChange = {},
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        label = { Text("Fecha $contexto") },
        trailingIcon = {
            IconButton(onClick = { mostrarPicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        }
    )

    // Dialog con DatePicker
    if (mostrarPicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarPicker = false
                        onFechaSeleccionada(
                            SimpleDateFormat(
                                formato,
                                Locale.getDefault()
                            ).format(Date(fechaTemp))
                        )
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarPicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            val datePickerState = rememberDatePickerState()
            fechaTemp = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
            DatePicker(state = datePickerState)
        }
    }
}

private var fechaTemp: Long = System.currentTimeMillis()