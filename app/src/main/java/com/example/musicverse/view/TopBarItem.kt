package com.example.musicverse.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.controller.InicioViewModel
import com.example.musicverse.data.UsuarioSession


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarItem(nav: NavController) {
    val viewModel: InicioViewModel = viewModel()

    TopAppBar(
        title = { Text("", fontWeight = FontWeight.Bold, color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6A1B9A)),
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable{
                        nav.navigate(Pantalla.Inicio.ruta)
                    }
                    .padding(16.dp)
            ) {
                Spacer(Modifier.width(16.dp))
                Text("🎵", fontSize = 24.sp, color = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("MusicVerse", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
            }
        },
        actions = {
            // Solo el icono del carrito
            IconButton(onClick = { nav.navigate(Pantalla.Carrito.ruta) }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color.White)
            }
            IconButton(onClick = { nav.navigate(Pantalla.ModificarUsuario.ruta) }) {
                Icon(Icons.Default.Settings, contentDescription = "Modificar", tint = Color.White)
            }
            if(UsuarioSession.rol.equals("Admin")){
                IconButton(onClick = { nav.navigate(Pantalla.ModificarUsuario.ruta) }) {
                    Icon(Icons.Default.Warning, contentDescription = "Modificar", tint = Color.White)
                }
            }
            IconButton(onClick = {
                viewModel.cerrarSession(nav)
            }) {
                Icon(Icons.Default.Close, contentDescription = "Modificar", tint = Color.White)
            }
        }
    )
}
