package com.example.musicverse.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.model.RegistroPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.musicverse.api.ApiService
import com.example.musicverse.model.MetodosPagoGet
import com.example.musicverse.network.RetrofitProvider
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

data class RegistroUiState(
    // creación
    val rut: String = "",
    val nombre: String = "",
    val correo: String = "",
    val genero: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val contrasenia: String = "",
    val fecha: String = "",
    val metodoPago: String = "",
    val metodoPagoInt: Int = 0,
    val isCreating: Boolean = false,
    val created: Response<Unit>? = null,
    val createError: String? = null,
    // listado
    val list: List<RegistroPost> = emptyList(),
    var metodosPago: List<MetodosPagoGet> = emptyList(),
    val isListLoading: Boolean = false,
    val listError: String? = null
)

class RegistroViewModel(app: Application) : AndroidViewModel(app) {

    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val _state = MutableStateFlow(RegistroUiState())
    val state: StateFlow<RegistroUiState> = _state.asStateFlow()

    init {
        obtenerMetodosPago()
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

    fun onGeneroChange(value: String) {
        _state.update { it.copy(genero = value, createError = null) }
    }

    fun onFechaChange(value: String) {
        _state.update { it.copy(fecha = value, createError = null) }
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

    fun crearRegistro(nav: NavController){
        val p = _state.value

        if(p.rut.isEmpty() || p.nombre.isEmpty() || p.correo.isEmpty()
            || p.contrasenia.isEmpty() || p.direccion.isEmpty() || p.telefono.isEmpty()){
            _state.update { it.copy(createError = "Todos los campos son obligatorios") }
            return
        }
        if(p.fecha.isEmpty()){
            _state.update { it.copy(createError = "Todos los campos son obligatorios") }
            return
        }

        if(p.genero.isEmpty()){
            _state.update { it.copy(createError = "Todos los campos son obligatorios") }
            return
        }
        if(p.metodoPagoInt==0){
            _state.update { it.copy(createError = "Todos los campos son obligatorios") }
            return
        }
        if (p.rut.length < 10 || !p.rut.contains("-")) {
            _state.update { it.copy(createError = "El RUT debe tener al menos 10 caracteres y un guion -") }
            return
        }

        // ESTA COSA ACEPTA REGEX LOL ahora me entero
        val regexCorreo = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.(com|cl|org|info)$".toRegex()
        if(!regexCorreo.matches(p.correo)){
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
            _state.update { it.copy(createError = "Formatos permitidos: ") }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isCreating = true, createError = null) }

                val nuevoPost = RegistroPost(
                    rut = p.rut,
                    nombre = p.nombre,
                    contrasenia = p.contrasenia,
                    correo = p.correo,
                    direccion = p.direccion,
                    telefono = p.telefono,
                    metodoPago = p.metodoPagoInt,
                    fechaNacimiento = p.fecha,
                    genero = p.genero
                )

                val creado = api.registrarUsuario(nuevoPost)
                if(creado.code()!=200){
                    _state.update {
                        it.copy(createError = "Error: El Usuario se encuentra registrado")
                    }
                }else{
                    nav.navigate(Pantalla.Intereses.crearRutaInteres(p.rut, 0))
                }
                _state.update {
                    it.copy(created = creado, isCreating = false)
                }

            }catch (e: Exception){
                _state.update { it.copy(isCreating = false, createError = e.message) }
            }
        }
    }

    private fun obtenerMetodosPago() {
        val p = _state.value
        viewModelScope.launch {
            try {
                p.metodosPago = api.obtenerMetodosPago()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}