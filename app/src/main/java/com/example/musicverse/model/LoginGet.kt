package com.example.musicverse.model

data class LoginGet(
    val mensaje: String,
    val rut: String,
    val usuario: String,
    val rol: String,
    val direccion: String,
    val telefono: String,
    val fecha: String,
    val metodoPago: Int,
    val genero: String
)
