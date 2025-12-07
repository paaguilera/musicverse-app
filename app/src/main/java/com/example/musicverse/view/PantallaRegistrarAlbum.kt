package com.example.musicverse.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.musicverse.Pantalla
import com.example.musicverse.controller.AlbumRegistroViewModel
import com.example.musicverse.controller.RegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistrarAlbum (nav: NavController) {
    val viewModel: AlbumRegistroViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var formato by remember { mutableStateOf("") }
    var upc by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var artista by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var expandedG by remember { mutableStateOf(false) }

    var imagenUri: Uri? by remember { mutableStateOf<Uri?>(null) }

    // Selector de imagen
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        Log.d("IMAGEN_DEBUG", "URI SELECCIONADA: $uri")
        if (uri != null) {
            viewModel.onImageChange(uri)
        }
    }

    Scaffold(
        topBar = {
            TopBarItem(nav)
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Text("Registro Album", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Color(0xFF6A1B9A))
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
                if (state.imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(state.imageUri),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )
                }

                Button(onClick = { launcher.launch("image/*")
                    println(state.imageUri) }) {
                    Text("Seleccionar Imagen")
                }

                Spacer(Modifier.height(16.dp))

                // Campos del formulario
                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = { viewModel.onNombreChange(it) },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.formato,
                    onValueChange = { viewModel.onFormatoChange(it) },
                    label = { Text("Formato (CD / Vinilo)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    SelectorFecha(
                        "yyyy-MM-dd",
                        "de salida",
                        fechaSeleccionada = state.fecha,
                        onFechaSeleccionada = { fecha -> viewModel.onFechaChange(fecha) }
                    )
                    Spacer(Modifier.height(16.dp))
                }
                OutlinedTextField(
                    value = state.upc,
                    onValueChange = { viewModel.onUpcChange(it) },
                    label = { Text("UPC") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.precio,
                    onValueChange = { viewModel.onPrecioChange(it) },
                    label = { Text("Precio") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.stock,
                    onValueChange = { viewModel.onStockChange(it) },
                    label = { Text("Stock") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.artista,
                    onValueChange = { viewModel.onArtistaChange(it) },
                    label = { Text("Artista") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Box {
                    OutlinedTextField(
                        value = state.genero,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        label = { Text("Seleccione un genero") },
                        trailingIcon = {
                            IconButton(onClick = { expandedG = !expandedG }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedG,
                        onDismissRequest = { expandedG = false }
                    ) {
                        state.generos.forEach { gen ->
                            DropdownMenuItem(
                                text = { Text("${gen.nombre}") },
                                onClick = {
                                    viewModel.onGeneroChange(gen.nombre)
                                    expandedG = false
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(6.dp))

                Button(
                    onClick = {
                        viewModel.registrarAlbum(nav)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A))
                ) {
                    Text("Registrar Álbum", color = Color.White)
                }
                Spacer(Modifier.height(6.dp))
                Button(
                    onClick = {
                        nav.navigate(Pantalla.Inicio.ruta)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF5A5A5A)),
                    modifier = Modifier.fillMaxWidth())
                { Text("Volver",
                    color = Color.White)
                }
            }
        }
    }
}

@Composable
fun RegistroInput(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
