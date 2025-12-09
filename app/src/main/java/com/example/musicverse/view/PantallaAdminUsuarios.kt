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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.musicverse.model.UsuarioMini

@Composable
fun PantallaAdminUsuarios(nav: NavController){
    val viewModel: AdminViewModel = viewModel()
    val initState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.listaUsuarios()
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
                    onClick = { viewModel.buscarUsuarios(initState.buscador) },
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
            Button(
                onClick = { viewModel.listaUsuarios() },
                colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
                modifier = Modifier.height(56.dp)
            ) {
                Text(
                    text = "Todos",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text("Usuarios", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.Black)
            }
            Spacer(Modifier.height(16.dp))
            if(initState.usuariosMini.isEmpty()){
                Text("No hay usuarios registrados", fontWeight = FontWeight.Medium, fontSize = 20.sp, color = Color.Black)
            }else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(initState.usuariosMini) { user ->
                        CardUsuarios(
                            user,
                            onClick = {
                                viewModel.borrarUsuario(rut = user.rut)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardUsuarios(user: UsuarioMini, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(2f)) {
                Text(user.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text("Correo: ${user.correo}", fontSize = 12.sp, color = Color.Gray)
                Text("Rut: ${user.rut}", fontSize = 12.sp, color = Color.Gray)
            }
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 8.dp)
            ) {
                Text("-",
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }
        }
    }
}