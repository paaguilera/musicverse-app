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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.carritoManagerViewModel

@Composable
fun PantallaCompraFinalizada(nav: NavController) {
    carritoManagerViewModel.clearCart()
    Column(
        Modifier.fillMaxSize().background(Color(0xFFF5F3FF)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎉 Gracias por su compra 🎉", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF6A1B9A))
        Spacer(Modifier.height(16.dp))
        Button(onClick = { nav.navigate(Pantalla.Inicio.ruta) }, colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A))) {
            Text("Volver al inicio", color = Color.White)
        }
    }
}
