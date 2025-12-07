package com.example.musicverse.model

data class CarritoItem (
    val albumId: Int,
    val nombre: String,
    val artista: String,
    val imagenUrl: String,
    val precio: Int,
    var cantidad: Int = 1,
    var desc: Double = 0.0
)