package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.controller.RegistroViewModel

@Composable
fun PantallaRegistro(nav: NavController) {
    val viewModel: RegistroViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    val opcionesGenero = listOf("Masculino", "Femenino", "Otro")

    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf(0) }
    var expandedGn by remember { mutableStateOf(false) }
    var expandedMt by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F3FF))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro de Usuario", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color(0xFF6A1B9A))
        Spacer(Modifier.height(24.dp))
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = state.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.rut,
            onValueChange = { viewModel.onRutChange(it) },
            label = { Text("Rut") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.correo,
            onValueChange = { viewModel.onCorreoChange(it) },
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            Modifier.padding(16.dp).fillMaxWidth()
        ) {
            SelectorFecha(
                "dd-MM-yyyy",
                "de nacimiento",
                fechaSeleccionada = state.fecha,
                onFechaSeleccionada = { fecha -> viewModel.onFechaChange(fecha) }
            )
            Spacer(Modifier.height(16.dp))
        }
        Box {
            OutlinedTextField(
                value = state.genero,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                readOnly = true,
                label = { Text("Genero") },
                trailingIcon = {
                    IconButton(onClick = { expandedGn = !expandedGn }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                singleLine = true
            )

            DropdownMenu(
                expanded = expandedGn,
                onDismissRequest = { expandedGn = false }
            ) {
                opcionesGenero.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            viewModel.onGeneroChange(opcion)
                            expandedGn = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = state.direccion,
            onValueChange = { viewModel.onDireccionChange(it) },
            label = { Text("Direccion") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.telefono,
            onValueChange = { viewModel.onTelefonoChange(it) },
            label = { Text("Telefono") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.contrasenia,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(6.dp))
        Box {
            OutlinedTextField(
                value = state.metodoPago,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Seleccione un método") },
                trailingIcon = {
                    IconButton(onClick = { expandedMt = !expandedMt }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )

            DropdownMenu(
                expanded = expandedMt,
                onDismissRequest = { expandedMt = false }
            ) {
                state.metodosPago.forEach { metodo ->
                    DropdownMenuItem(
                        text = { Text("${metodo.tipo} - ${metodo.nombre}") },
                        onClick = {
                            viewModel.onMetodoPagoChange(metodo.nombre,metodo.id_metodopago)
                            expandedMt = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.crearRegistro(nav)
                      },
            colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
            modifier = Modifier.fillMaxWidth())
        { Text("Registrarse",
            color = Color.White)
        }
        Button(
            onClick = {
                nav.navigate(Pantalla.Carga.ruta)
            },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier.fillMaxWidth())
        { Text("Volver",
            color = Color.White)
        }
        state.createError?.let {
            InputChip(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                label = { Text(it, fontSize = 15.sp) },
                selected = false,
                colors = InputChipDefaults.inputChipColors(
                    containerColor = Color.Red,
                    labelColor = MaterialTheme.colorScheme.onPrimary,
                    trailingIconColor = MaterialTheme.colorScheme.onPrimary,
                ),
                trailingIcon = {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Localized description",
                        Modifier.size(InputChipDefaults.AvatarSize)
                    )
                },
            )
        }
    }

}