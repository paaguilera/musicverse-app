package com.example.musicverse.model

data class ProductoLista(
    val id: Int,
    val nombre: String,
    val artista: String,
    val genero: String,
    val precio: Int,
    val imagenUrl: String
)