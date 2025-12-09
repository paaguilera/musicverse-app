package com.example.musicverse.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musicverse.Pantalla
import com.example.musicverse.model.ProductoLista

@Composable
fun CardAlbumItem(producto: ProductoLista, onClick: () -> Unit, admin: Boolean) {
    var onClick1: () -> Unit
    var onClick2: () -> Unit
    var textoDesa = "Habilitado"
    if(admin){
        onClick1 = {}
        onClick2 = onClick
    }else{
        onClick1 = onClick
        onClick2 = {}
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick1
            )
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Simulación de imagen (similar al diseño del carrito)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFD1C4E9), RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text("Artista: ${producto.artista}", fontSize = 12.sp, color = Color.Gray)
                Text("Género: ${producto.genero}", fontSize = 12.sp, color = Color.Gray)
            }
            // Precio
            // CORRECCIÓN: Se quitó el primer "$" ya que valorFormateado ya lo tiene.
            if(admin){
                Column(modifier = Modifier.weight(1f)) {
                    Text("$"+producto.precio.toString(), fontWeight = FontWeight.SemiBold, color = Color(0xFF6A1B9A), fontSize = 14.sp)
                    if(producto.desabilidato){
                        textoDesa = "Desabilitado"
                    }
                    Text(textoDesa, fontWeight = FontWeight.SemiBold, color = Color(0xFF6A1B9A), fontSize = 14.sp)
                }
                if(producto.desabilidato){
                    Button(
                        onClick = onClick2,
                        colors = ButtonDefaults.buttonColors(Color(0xFF1DC70D)),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(40.dp)
                            .wrapContentWidth())
                    { Text("+",
                        color = Color.White,
                        fontSize = 20.sp,)
                    }
                }else{
                    Button(
                        onClick = onClick2,
                        colors = ButtonDefaults.buttonColors(Color(0xFFCE0303)),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(40.dp)
                            .wrapContentWidth())
                    { Text("-",
                        color = Color.White,
                        fontSize = 20.sp,)
                    }
                }
            }else{
                Column(modifier = Modifier.weight(1f)) {
                    Text("$"+producto.precio.toString(), fontWeight = FontWeight.SemiBold, color = Color(0xFF6A1B9A), fontSize = 14.sp)
                }
            }
        }
    }
}