package com.example.musicverse.model

data class RegistroPost(
    val rut: String,
    val nombre: String,
    val correo: String,
    val direccion: String,
    val telefono: String,
    val contrasenia: String,
    val fechaNacimiento: String,
    val genero: String,
    val metodoPago: Int
)
