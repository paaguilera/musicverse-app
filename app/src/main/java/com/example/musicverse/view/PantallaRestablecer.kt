package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicverse.Pantalla

@Composable
fun PantallaRestablecer(nav: NavController) {
    var correo by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().background(Color(0xFFF5F3FF)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Restablecer contraseña", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color(0xFF6A1B9A))
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(24.dp))
        Button(onClick = { nav.navigate(Pantalla.CorreoEnviado.ruta) }, colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)), modifier = Modifier.fillMaxWidth()) {
            Text("Enviar", color = Color.White)
        }
    }
}