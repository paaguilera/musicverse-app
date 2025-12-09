package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.controller.InicioViewModel
import com.example.musicverse.controller.ProductosViewModel
import com.example.musicverse.data.UsuarioSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(nav: NavController) {
    val viewModel: InicioViewModel = viewModel()
    val prodViewModel: ProductosViewModel = viewModel()

    var generoSeleccionado by remember { mutableStateOf<String?>(null) }

    val state by prodViewModel.state.collectAsState()
    val initState by viewModel.state.collectAsState()

    val rut by viewModel.rut.observeAsState("")
    LaunchedEffect(Unit) {
        viewModel.obtenerRutDAO()
        viewModel.obtenerGeneros()
        prodViewModel.obtenerProductosLista(-1, rut)
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
            horizontalAlignment = Alignment.CenterHorizontally // Centrar
        ) {
            Spacer(Modifier.height(8.dp))
            Text(text = "Bienvenid@ ${UsuarioSession.correo}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black, modifier = Modifier.align(Alignment.Start))
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
                    onValueChange = { viewModel.onBuscadorChange(it) },
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
            Spacer(Modifier.height(16.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
               items(items = initState.generos){ genero ->
                   Button(onClick = {
                       prodViewModel.obtenerProductosLista(genero.idGenero, rut)
                   }) {
                       Text(text = genero.nombre)
                   }
               }
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text("Explorar Álbumes", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.Black)
                Button(
                    onClick = {
                        nav.navigate(Pantalla.AlbumRegistro.ruta)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(40.dp)
                        .wrapContentWidth())
                { Text("+",
                    color = Color.White,
                    fontSize = 20.sp,)
                }
            }
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.productosMini) { album ->
                    if(!album.desabilidato){
                        CardAlbumItem(
                            album,
                            onClick = {
                                nav.navigate(Pantalla.Detalle.crear(album.nombre))
                            },
                            false
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}