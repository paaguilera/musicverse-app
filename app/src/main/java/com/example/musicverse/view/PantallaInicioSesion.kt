package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.musicverse.controller.LoginViewModel
import com.example.musicverse.controller.RegistroViewModel

@Composable
fun PantallaInicioSesion(nav: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F3FF))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color(0xFF6A1B9A))
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = state.correo,
            onValueChange = { viewModel.onCorreoChange(it) },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.contrasenia,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "¿Olvidó su contraseña?",
            color = Color(0xFF6A1B9A),
            modifier = Modifier.clickable { nav.navigate(Pantalla.Restablecer.ruta) }
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            viewModel.crearLoginPost(nav)
                       },
            colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
            modifier = Modifier.fillMaxWidth())
        {
            Text("Acceder", color = Color.White)
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
        Spacer(Modifier.height(24.dp))
        Spacer(Modifier.height(24.dp))
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