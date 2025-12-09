package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.example.musicverse.controller.AdminViewModel
import com.example.musicverse.controller.InicioViewModel
import com.example.musicverse.controller.ProductosViewModel

@Composable
fun PantallaAdmin(nav: NavController){

    val initViewModel: InicioViewModel = viewModel()
    val initState by initViewModel.state.collectAsState()

    val prodViewModel: ProductosViewModel = viewModel()
    val prodState by prodViewModel.state.collectAsState()

    val adminViewModel: AdminViewModel = viewModel()
    val adminState by adminViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        prodViewModel.obtenerProductosLista(-1,"")
    }

    Scaffold(
        topBar = {
            TopBarItem(nav)
        }
    ) {
        padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F3FF))
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .weight(0.4f)
                        .clickable(onClick = { nav.navigate(Pantalla.AlbumRegistro.ruta) }),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Icon(
                                Icons.Default.AddCircle,
                                contentDescription = "Agregar album",
                                tint = Color.Black,
                                modifier = Modifier.size(50.dp)
                            )
                            Text("Agregar", fontWeight = FontWeight.Bold,fontSize = 20.sp)
                            Text("album", fontWeight = FontWeight.Bold,fontSize = 20.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Card(
                    modifier = Modifier
                        .weight(0.4f)
                        .clickable(onClick = { nav.navigate(Pantalla.AdminUsers.ruta) }),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Gestión de Usuarios",
                                tint = Color.Black,
                                modifier = Modifier.size(50.dp)
                            )
                            Text("Gestión de", fontWeight = FontWeight.Bold,fontSize = 20.sp)
                            Text("usuarios", fontWeight = FontWeight.Bold,fontSize = 20.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = initState.buscador,
                    onValueChange = { initViewModel.onBuscadorChange(it) },
                    label = { Text("Buscador") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                )
                Button(
                    onClick = { prodViewModel.buscadorBoton(initState.buscador) },
                    colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(
                        text = "Buscar",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text("Explorar Álbumes", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.Black)
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(prodState.productosMini) { album ->
                    CardAlbumItem(
                        album,
                        onClick = {
                            adminViewModel.estadoAlbum(album.id,!album.desabilidato)
                            nav.navigate(Pantalla.Admin.ruta)
                                  },
                        true)
                }
            }
        }
    }
}