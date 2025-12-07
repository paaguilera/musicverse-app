package com.example.musicverse.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val rut: String,
    val nombre: String,
    val correo: String,
    val contrasenia: String,
    val rol: String,
    val direccion: String,
    val telefono: String,
    val fecha: String,
    val metodoPago: Int,
    val genero: String
)
