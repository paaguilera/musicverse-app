package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicverse.Pantalla
import com.example.musicverse.carritoManagerViewModel
import com.example.musicverse.controller.ProductosViewModel
import com.example.musicverse.model.ProductoGrand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(nav: NavController, albumNombre: String) {

    val prodViewModel: ProductosViewModel = viewModel()
    val state by prodViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        prodViewModel.obtenerProductoNombre(albumNombre)
    }

    // Buscar datos del álbum (simulación)
    val album: ProductoGrand? = state.productoGrand

    Scaffold(
        topBar = {
            TopBarItem(nav)
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F3FF))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nombre: ${album?.nombre}", color = Color.Black)
            Spacer(Modifier.height(12.dp))
            AsyncImage(
                model = album?.imagenUrl,
                contentDescription = album?.nombre,
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.height(12.dp))
            Text("Artista: ${album?.artista}", color = Color.Gray)
            // CORRECCIÓN: Se quitó el primer "$" ya que valorFormateado ya lo tiene.
            Spacer(Modifier.height(12.dp))
            Text("Precio: ${album?.precio}", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(24.dp))
            println(album?.idAlbum)
            Button(
                onClick = {
                    carritoManagerViewModel.addToCart(album)
                    nav.navigate(Pantalla.Carrito.ruta)
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A))
            ) {
                Text("Agregar al carrito", color = Color.White)
            }
        }
    }
}