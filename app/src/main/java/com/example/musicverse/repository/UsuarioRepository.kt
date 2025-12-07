package com.example.musicverse.repository

import com.example.musicverse.db.AppDatabase
import com.example.musicverse.dto.ModificarDTO
import com.example.musicverse.model.Usuario

class UsuarioRepository(private val db: AppDatabase) {
    suspend fun insert(u: Usuario) = db.usuarioDAO().insert(u)
    suspend fun delete(u: Usuario) = db.usuarioDAO().delete(u)
    suspend fun getPorMail(mail: String) = db.usuarioDAO().obtenerPorCorreo(mail)
    suspend fun obtenerUsuarios() = db.usuarioDAO().obtenerUsuarios();
    suspend fun getPorRut(rut: String) = db.usuarioDAO().obtenerPorRut(rut)
    suspend fun actualizarUsuario(dto: ModificarDTO, rut: String) = db.usuarioDAO().updateUser(
        rut = rut,
        telefono = dto.telefono,
        correo = dto.correo,
        direccion = dto.direccion,
        nombre = dto.nombre,
        pass = dto.contrasenia,
        genero = dto.genero,
        fecha = dto.fecha,
        metodoPago = dto.metodoPago
    )
}