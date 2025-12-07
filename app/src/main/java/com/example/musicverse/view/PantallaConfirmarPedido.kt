package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musicverse.carritoManagerViewModel
import com.example.musicverse.controller.ConfirmarPedidoViewModel
import com.example.musicverse.model.QrScannerScreen
import kotlinx.coroutines.delay

@Composable
fun InputFieldPedido(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        enabled = false,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        modifier = modifier.height(56.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF6A1B9A),
            unfocusedBorderColor = Color.LightGray
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaConfirmarPedido(nav: NavController) {
    val confPedidoVM: ConfirmarPedidoViewModel = viewModel()
    val state by confPedidoVM.state.collectAsState()

    LaunchedEffect(Unit) {
        delay(1000)
        confPedidoVM.obtenerDatosUsuario()
    }

    // Estados para los campos de formulario
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var metodopago by remember { mutableStateOf("") }
    var qrValue by remember { mutableStateOf("") }
    var showScanner by remember { mutableStateOf(false) }
    val totalFormatted = carritoManagerViewModel.getTotal()

    if (showScanner) {
        QrScannerScreen(
            onQrScanned = { value ->
                qrValue = value
                val qrRecibo = confPedidoVM.recibirQRDesc(qrValue)
                if(qrRecibo != null){
                    confPedidoVM.aplicarDescuentoAProducto(
                        qrRecibo.id_album,
                        qrRecibo.desc
                    )
                    qrValue = "QR Escaneado con exito"
                }else{
                    qrValue = "Error"
                }
                showScanner = false
            },
            onClose = {
                showScanner = false
            }
        )
        return
    }

    Scaffold(
        topBar = {
            TopBarItem(nav)
        }
    ) { padding ->
        LazyColumn(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F3FF))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Confirmar pedido", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
                    // Se quitó el segundo "$"
                    Text("Total: $$totalFormatted", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF6A1B9A))
                }
                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InputFieldPedido(state.nombre, { state.nombre }, "Ingresar nombre", Modifier.weight(1f))
                    InputFieldPedido(state.rut, { state.rut }, "Ingresar rut", Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InputFieldPedido(state.correo, { state.correo }, "Ingresar correo", Modifier.weight(1f))
                    InputFieldPedido(state.telefono, { state.telefono}, "Ingresar número", Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    InputFieldPedido(state.direccion, { state.direccion }, "Ingresar dirección", Modifier.weight(1f))
                    InputFieldPedido(state.metodoPago, { state.metodoPago }, "Ingresar mtdpago", Modifier.weight(1f))
                }
                Spacer(Modifier.height(16.dp))
                carritoManagerViewModel.cartItems.collectAsState().value.forEach { producto ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)){
                        InputFieldPedido(producto.nombre, { state.direccion }, "Album:", Modifier.weight(1f))
                        InputFieldPedido(producto.cantidad.toString(), { state.metodoPago }, "Cantidad:", Modifier.weight(1f))
                        InputFieldPedido(((producto.desc)*100).toInt().toString()+"%", {}, "Descuento:", Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(16.dp))
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Valor QR: $qrValue")

                    Button(onClick = { showScanner = true }) {
                        Text("Escanear QR")
                    }
                }
                // Botón compra
                Button(
                    onClick = {
                        confPedidoVM.procesarCompra(nav)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Finalizar compra", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}