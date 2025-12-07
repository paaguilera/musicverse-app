package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicverse.Pantalla

@Composable
fun PantallaCorreoEnviado(nav: NavController) {
    Column(
        Modifier.fillMaxSize().background(Color(0xFFF5F3FF)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Se ha enviado una notificación a su correo.", textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Button(onClick = { nav.navigate(Pantalla.Bienvenida.ruta) }, colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A))) {
            Text("Volver al inicio", color = Color.White)
        }
    }
}