package com.example.musicverse.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.api.ApiService
import com.example.musicverse.dto.ModificarDTO
import com.example.musicverse.model.MetodosPagoGet
import com.example.musicverse.model.RegistroPost
import com.example.musicverse.model.Usuario
import com.example.musicverse.network.RetrofitProvider
import com.example.musicverse.service.UsuarioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent

data class ModificarUiState(
    // creación
    val rut: String = "",
    val nombre: String = "",
    val correo: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val contrasenia: String = "",
    val genero: String = "",
    val fecha: String = "",
    val metodoPago: String = "",
    val metodoPagoInt: Int = 0,
    val created: String = "",
    val isCreating: Boolean = false,
    val createError: String? = null,
    // listado
    var metodosPago: List<MetodosPagoGet> = emptyList(),
    val isListLoading: Boolean = false,
    val listError: String? = null
)

class ModificarViewModel (app: Application): AndroidViewModel(app){
    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val service = UsuarioService(app)
    private val _state = MutableStateFlow(ModificarUiState())

    val state: StateFlow<ModificarUiState> = _state.asStateFlow()

    var usuarios: List<Usuario> = emptyList()

    init {
        obtenerMetodosPago()
        obtenerDatosUsuario()
    }
    fun obtenerDatosUsuario(){
        viewModelScope.launch{
            usuarios = obtenerUsuarios()
            var usuario: Usuario = usuarios.get(0)
            _state.update { it.copy(
                nombre = usuario.nombre,
                direccion = usuario.direccion,
                correo = usuario.correo,
                contrasenia = usuario.contrasenia,
                telefono = usuario.telefono,
                rut = usuario.rut,
                genero = usuario.genero,
                metodoPagoInt = usuario.metodoPago,
                fecha = usuario.fecha,
            ) }
            cargarMetodoPago()
        }
    }

    suspend fun obtenerUsuarios(): List<Usuario>{
        return service.obtenerUsuarios()
    }

    fun onRutChange(value: String) {
        _state.update { it.copy(rut = value, createError = null) }
    }

    fun onNombreChange(value: String) {
        _state.update { it.copy(nombre = value, createError = null) }
    }

    fun onCorreoChange(value: String) {
        _state.update { it.copy(correo = value, createError = null) }
    }

    fun onDireccionChange(value: String) {
        _state.update { it.copy(direccion = value, createError = null) }
    }

    fun onTelefonoChange(value: String) {
        _state.update { it.copy(telefono = value, createError = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(contrasenia = value, createError = null) }
    }
    fun onMetodoPagoChange(value1: String,value2: Int) {
        _state.update { it.copy(metodoPago = value1, metodoPagoInt = value2, createError = null) }
    }
    fun onGeneroChange(value: String) {
        _state.update { it.copy(genero = value, createError = null) }
    }

    fun onFechaChange(value: String) {
        _state.update { it.copy(fecha = value, createError = null) }
    }
    fun crearModificacion(nav: NavController){
        val p = _state.value

        if(p.rut.isEmpty() || p.nombre.isEmpty() || p.correo.isEmpty()
            || p.contrasenia.isEmpty() || p.direccion.isEmpty() || p.telefono.isEmpty()){
            _state.update { it.copy(createError = "Todos los campos son obligatorios") }
            return
        }

        // ESTA COSA ACEPTA REGEX LOL ahora me entero
        val regex = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.(com|cl|org|info)$".toRegex()
        if(!regex.matches(p.correo)){
            _state.update { it.copy(createError = "Correo Invalido") }
            return
        }

        if(p.contrasenia.length < 6){
            _state.update { it.copy(createError = "La contraseña debe tener por lo menos 6 caracteres") }
            return
        }

        if(p.telefono.length < 9){
            _state.update { it.copy(createError = "El telefono debe tener minimo 6 numeros") }
            return
        }
        val regexTelefono= "^\\+?[0-9]+$".toRegex()
        if(!regexTelefono.matches(p.telefono)){
            _state.update { it.copy(createError = "Ingrese un numero valido") }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isCreating = true, createError = null) }

                val nuevoPut = ModificarDTO(
                    nombre = p.nombre,
                    contrasenia = p.contrasenia,
                    correo = p.correo,
                    direccion = p.direccion,
                    telefono = p.telefono,
                    metodoPago = p.metodoPagoInt,
                    fecha = p.fecha,
                    genero = p.genero
                )

                service.modiciarUsuario(nuevoPut,p.rut)
                val creado = api.updateUser(p.rut, nuevoPut)
                if(creado.isSuccessful){
                    nav.navigate(Pantalla.Inicio.ruta)
                }
                _state.update { it.copy(created = "ok", isCreating = false) }
            }catch (e: Exception){
                _state.update { it.copy(isCreating = false, createError = e.message) }
            }
        }
    }
    private fun obtenerMetodosPago() {
        val p = _state.value
        viewModelScope.launch {
            try {
                _state.update { it.copy(metodosPago = api.obtenerMetodosPago()) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun cargarMetodoPago(){
        val p = _state.value
        viewModelScope.launch {
            val metodospago = p.metodosPago
            metodospago.forEach { metodo ->
                if(metodo.id_metodopago == p.metodoPagoInt){
                    _state.update { it.copy(metodoPago = metodo.nombre) }
                }
            }
        }
    }
}