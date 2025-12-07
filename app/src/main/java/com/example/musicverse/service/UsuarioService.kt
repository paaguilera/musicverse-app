package com.example.musicverse.service

import android.app.Application
import com.example.musicverse.data.UsuarioSession
import com.example.musicverse.db.AppDatabase
import com.example.musicverse.dto.LoginDTO
import com.example.musicverse.dto.ModificarDTO
import com.example.musicverse.model.Usuario
import com.example.musicverse.repository.UsuarioRepository

class UsuarioService(App: Application){
    private val usuarioRepository = UsuarioRepository(AppDatabase.get(App))

    suspend fun registarUsuario(dto: LoginDTO){
        var existe: Boolean = false

        val usuario = Usuario(
            id = 0,
            rut = dto.rut,
            nombre = dto.usuario,
            correo = dto.correo,
            contrasenia = dto.contrasenia,
            rol = dto.rol,
            telefono = dto.telefono,
            direccion = dto.direccion,
            fecha = dto.fecha,
            metodoPago = dto.metodoPago,
            genero = dto.genero
        )
        val usuarios = usuarioRepository.obtenerUsuarios()
        UsuarioSession.iniciarLogin(usuario.rol, usuario.correo)
        usuarios.forEach {
            user ->
            if(user.correo.equals(usuario.correo)){
                existe = true
            }
        }
        if(!existe) {
            usuarioRepository.insert(usuario)
        }
    }
    suspend fun modiciarUsuario(dto: ModificarDTO, rut: String): Int{
        UsuarioSession.cambiarCorreo(dto.correo)
        val hola = usuarioRepository.actualizarUsuario(dto, rut)
        return hola
    }
    suspend fun obtenerCorreo(): String{
        val usuarios = usuarioRepository.obtenerUsuarios()
        var correo: String = "";

        usuarios.forEach { usuario -> correo = usuario.correo }
        return correo
    }
    suspend fun obtenerRut(): String{
        val usuarios = usuarioRepository.obtenerUsuarios()
        var rut: String = "";

        usuarios.forEach { usuario -> rut = usuario.rut }
        return rut
    }
    suspend fun truncarTabla() {
        UsuarioSession.cerrarSesion()
        val usuarios = usuarioRepository.obtenerUsuarios()
        usuarios.forEach { usuario -> usuarioRepository.delete(usuario) }
    }
    suspend fun obtenerUsuarios(): List<Usuario>{
        return usuarioRepository.obtenerUsuarios()
    }

}