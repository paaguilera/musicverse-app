package com.example.musicverse.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.musicverse.Pantalla
import com.example.musicverse.api.ApiService
import com.example.musicverse.db.AppDatabase
import com.example.musicverse.dto.LoginDTO
import com.example.musicverse.model.LoginGet
import com.example.musicverse.model.LoginPost
import com.example.musicverse.model.Usuario
import com.example.musicverse.network.RetrofitProvider
import com.example.musicverse.repository.UsuarioRepository
import com.example.musicverse.service.UsuarioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    // creación
    val rut: String = "",
    val nombre: String = "",
    val correo: String = "",
    val contrasenia: String = "",
    val rol: String = "",
    val isCreating: Boolean = false,
    val created: LoginGet? = null,
    val createError: String? = null,
    val loginState: Boolean = false,
    // listado
    val list: List<LoginGet> = emptyList(),
    val isListLoading: Boolean = false,
    val listError: String? = null
)
class LoginViewModel(app: Application): AndroidViewModel(app){

    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }

    private val service = UsuarioService(app)
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onCorreoChange(value: String) {
        _state.update { it.copy(correo = value, createError = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(contrasenia = value, createError = null) }
    }
    fun crearLoginPost(nav: NavController){
        val p = _state.value

        if(p.correo.isEmpty() || p.contrasenia.isEmpty()){
            _state.update { it.copy(createError = "Todos los campos son obligatorios") }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isCreating = true, createError = null) }

                val nuevoPost = LoginPost(
                    correo = p.correo,
                    contrasenia = p.contrasenia
                )
                val creado = api.loginUsuario(nuevoPost)
                if(creado.mensaje.equals("ok")){
                    val preDto = LoginDTO(
                        rol = creado.rol,
                        rut = creado.rut,
                        usuario = creado.usuario,
                        contrasenia = p.contrasenia,
                        correo = p.correo,
                        direccion = creado.direccion,
                        telefono = creado.telefono,
                        genero = creado.genero,
                        metodoPago = creado.metodoPago,
                        fecha = creado.fecha
                    )
                    service.truncarTabla()
                    service.registarUsuario(preDto)
                    _state.update { it.copy(created = creado, isCreating = false, loginState = true) }
                    nav.navigate(Pantalla.Inicio.ruta)
                }else{
                    _state.update { it.copy(isCreating = false, createError = creado.mensaje) }
                }

            }catch (e: Exception){
                _state.update { it.copy(isCreating = false, createError = "Datos Invalidos o vacios") }
            }
        }
    }
}