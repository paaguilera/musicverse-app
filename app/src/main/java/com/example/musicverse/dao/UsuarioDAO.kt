package com.example.musicverse.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.musicverse.dto.ModificarDTO
import com.example.musicverse.model.Usuario
import retrofit2.http.GET

@Dao
interface UsuarioDAO {

    @Insert
    suspend fun insert(Usuario: Usuario): Long

    @Delete
    suspend fun delete(Usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE correo = :mail")
    suspend fun obtenerPorCorreo(mail: String): Usuario?

    @Query("SELECT * FROM usuario")
    suspend fun obtenerUsuarios(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE rut = :rut")
    suspend fun obtenerPorRut(rut: String): Usuario?

    @Query("UPDATE usuario SET contrasenia = :pass, correo = :correo," +
            "direccion = :direccion, nombre = :nombre, telefono = :telefono," +
            "fecha = :fecha, metodoPago = :metodoPago, genero =:genero WHERE rut = :rut")
    suspend fun updateUser(pass: String, correo: String, direccion: String, nombre: String,
                           telefono: String, rut: String, fecha: String, metodoPago: Int, genero: String): Int

}