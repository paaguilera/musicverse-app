package com.example.musicverse.model

data class AlbumCrearPost(
    val nombre: String = "",
    val formato: String = "",
    val codeUPC: Long = 0,
    val fecha_lanza: String = "",
    val precio: Int = 0,
    val stock: Int = 0,
    val artista: String = "",
    val genero: String = ""
)