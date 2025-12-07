package com.example.musicverse.dto

data class LoginDTO(
    val rut: String,
    val usuario: String,
    val correo: String,
    val contrasenia: String,
    val rol: String,
    val direccion: String,
    val telefono: String,
    val fecha: String,
    val metodoPago: Int,
    val genero: String
)
