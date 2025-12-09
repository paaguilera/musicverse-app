package com.example.musicverse.model

import java.time.LocalDate

data class ProductoGrand(
    val idAlbum: Int,
    val nombre: String,
    val formato: String,
    val codeUPC: Long,
    val fecha_lanza: LocalDate,
    val precio: Int,
    val stock: Int,
    val imagenUrl: String,
    val artista: String,
    val canciones: List<String>,
    val resenias: List<String>,
    val genero: String,
    val desabilidato: Boolean
)