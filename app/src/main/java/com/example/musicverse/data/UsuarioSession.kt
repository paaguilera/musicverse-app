package com.example.musicverse.data

object UsuarioSession {
    var rol: String? = null
    var correo: String? = null

    fun iniciarLogin(uRol: String, uCorreo: String){
        correo = uCorreo
        rol = uRol
    }
    fun cambiarCorreo(uCorreo: String){
        correo = uCorreo
    }
    fun cerrarSesion(){
        correo = null
        rol = null
    }
    fun rolSingleton(): String?{
        return rol
    }
    fun correoSingleton(): String?{
        return correo
    }
}