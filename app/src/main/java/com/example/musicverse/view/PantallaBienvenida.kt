package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicverse.Pantalla

@Composable
fun PantallaBienvenida(nav: NavController) {
    Column(
        Modifier.fillMaxSize().background(Color(0xFFF5F3FF)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("MusicVerse", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))
        Spacer(Modifier.height(16.dp))
        Text(
            "¡Hola y bienvenido! Sumérgete en el mundo de la música y descubre tus artistas favoritos.",
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = { nav.navigate(Pantalla.Registro.ruta) },
            colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Registrarse", color = Color.White) }

        Spacer(Modifier.height(16.dp))
        OutlinedButton(
            onClick = { nav.navigate(Pantalla.InicioSesion.ruta) },
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Acceder", color = Color(0xFF6A1B9A)) }
    }
}