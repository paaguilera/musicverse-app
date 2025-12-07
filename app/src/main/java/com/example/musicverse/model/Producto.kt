package com.example.musicverse.model

data class Producto(
    val nombre: String,
    val artista: String,
    val genero: String,
    val precioCentavos: Int

) {
    // La variable formateada ya incluye el símbolo '$'
    val valorFormateado: String
        get() = "$"+String.format("%,.3f", precioCentavos.toDouble() / 1000).replace(',', '.')
}
