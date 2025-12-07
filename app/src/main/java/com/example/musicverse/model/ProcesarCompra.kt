package com.example.musicverse.model

data class ProcesarCompra(
    val rutUsuario: String = "",
    val idMetodoPago: Int = 0,
    val productos: List<ProcesarCompraProd> = emptyList()
)