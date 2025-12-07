package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicverse.Pantalla
import com.example.musicverse.carritoManagerViewModel
import com.example.musicverse.model.CarritoItem
import androidx.compose.runtime.collectAsState

@Composable
fun CartItemRow(
    item: CarritoItem,
    nav: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Imagen del álbum
        AsyncImage(
            model = item.imagenUrl, // tu URL viene del backend
            contentDescription = item.nombre,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text("Artista: ${item.artista}", fontSize = 12.sp, color = Color.Gray)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Valor: $${item.precio}",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6A1B9A),
                fontSize = 14.sp
            )
        }
    }

    //  MAS Y MENOS
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(12.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Button(
            onClick = {
                carritoManagerViewModel.decreaseQuantity(item.albumId)
                nav.navigate(Pantalla.Carrito.ruta)
                      },
            colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
            modifier = Modifier.height(35.dp)
        ) {
            Text("-", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
        }

        Text("Cantidad: ${item.cantidad}", fontWeight = FontWeight.Bold)

        Button(
            onClick = {
                carritoManagerViewModel.increaseQuantity(item.albumId)
                nav.navigate(Pantalla.Carrito.ruta)
                      },
            colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
            modifier = Modifier.height(35.dp)
        ) {
            Text("+", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
        }

        // QUITAR TODO
        Button(
            onClick = {
                carritoManagerViewModel.removeFromCart(item.albumId)
                nav.navigate(Pantalla.Carrito.ruta)
                      },
            colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
            modifier = Modifier.height(35.dp)
        ) {
            Text("Quitar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
        }
    }
}

@Composable
fun CartItemList(
    items: List<CarritoItem>,
    navController: NavController
) {
    if (items.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("El carrito está vacío.", color = Color.Gray, fontSize = 18.sp)
            Spacer(Modifier.height(16.dp))
            OutlinedButton(onClick = { navController.navigate("inicio") }) {
                Text("Explorar música")
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            items(items) { item ->
                CartItemRow(item = item, navController)
            }
        }
    }
}
@Composable
fun PantallaCarrito(nav: NavController) {

    val items = carritoManagerViewModel.cartItems.collectAsState().value
    println("----------------------------")
    println(items)
    Scaffold(
        topBar = { TopBarItem(nav) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F3FF))
                .padding(horizontal = 24.dp)
        ) {

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Carrito de compra", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                if (items.isNotEmpty()) {
                    Text(
                        "Total: $${carritoManagerViewModel.getTotal()}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color(0xFF6A1B9A)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            if (items.isNotEmpty()) {
                Text("Tus productos", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Gray)
            }

            CartItemList(items, nav)

            Spacer(Modifier.height(16.dp))

            if (items.isNotEmpty()) {
                Button(
                    onClick = { nav.navigate(Pantalla.ConfirmarPedido.ruta) },
                    colors = ButtonDefaults.buttonColors(Color(0xFF6A1B9A)),
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                ) {
                    Text("Continuar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}