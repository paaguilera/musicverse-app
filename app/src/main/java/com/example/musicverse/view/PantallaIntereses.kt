package com.example.musicverse.view

import androidx.collection.MutableIntList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.controller.GenerosUsuarioViewModel

@Composable
fun PantallaIntereses(nav: NavController, rut: String, origen: Int) {
    val viewModel: GenerosUsuarioViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val generos = listOf("","Rock", "Metal", "Rap", "Trap", "Pop", "Jazz", "Reggaeton", "Indie", "Hip Hop")
    val seleccionados = remember { mutableStateListOf<String>() }
    val seleccion: MutableList<Int> = mutableListOf()

    if(origen == 1) {
        LaunchedEffect(Unit) {
            val seleccionPrevia = viewModel.obtenerGeneros(rut)
            println(viewModel.obtenerGeneros(rut))
            seleccion.addAll(seleccionPrevia)

            seleccionados.clear()
            seleccionados.addAll(seleccionPrevia.map { generos[it] })
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F3FF))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona tus géneros favoritos", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color(0xFF6A1B9A))
        Spacer(Modifier.height(16.dp))
        generos.forEach { genero ->
            if(genero.isEmpty()){}else {
                val seleccionado = seleccionados.contains(genero)
                Button(
                    onClick = {
                        if (seleccionado) {
                            seleccionados.remove(genero)
                        } else {
                            seleccionados.add(genero)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(if (seleccionado) Color(0xFF6A1B9A) else Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(genero, color = if (seleccionado) Color.White else Color(0xFF6A1B9A))
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            seleccionados.forEach { i ->
                seleccion.add(generos.indexOf(i))
            }
            viewModel.crearGenerosUsuario(seleccion, rut)
            if(origen == 0){
                nav.navigate(Pantalla.Bienvenida.ruta)
            }else{
                nav.navigate(Pantalla.Inicio.ruta)
            }
                         },
            colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
            modifier = Modifier.fillMaxWidth()) {
            Text("Continuar", color = Color.White)
        }
    }
}