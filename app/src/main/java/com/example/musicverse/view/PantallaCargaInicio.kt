package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.controller.CargarViewModel
import kotlinx.coroutines.delay

@Composable
fun PantallaCargaInicio(nav: NavController) {
    val viewModel: CargarViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        delay(2500)
        viewModel.cargarLogin(nav)
        if(!state.loginState){
            nav.navigate(Pantalla.Bienvenida.ruta) { popUpTo(0) }
        }
    }
    Box(
        Modifier.fillMaxSize().background(Color(0xFFF5F3FF)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("🎵", fontSize = 60.sp)
            Text("MusicVerse", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF6A1B9A))
            Spacer(Modifier.height(8.dp))
            Text("Cargando...", color = Color.Gray, fontSize = 14.sp)
        }
    }
}