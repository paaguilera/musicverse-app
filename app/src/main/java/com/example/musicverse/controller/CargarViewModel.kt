package com.example.musicverse.controller

import android.app.Application
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.api.ApiService
import com.example.musicverse.data.UsuarioSession
import com.example.musicverse.dto.LoginDTO
import com.example.musicverse.model.LoginGet
import com.example.musicverse.model.LoginPost
import com.example.musicverse.model.Usuario
import com.example.musicverse.network.RetrofitProvider
import com.example.musicverse.service.UsuarioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CargaUiState(
    val isCreating: Boolean = false,
    val created: LoginGet? = null,
    val createError: String? = null,
    val loginState: Boolean = false
    )

class CargarViewModel (app: Application): AndroidViewModel(app){

    private val service = UsuarioService(app)
    private val _state = MutableStateFlow(CargaUiState())
    val state: StateFlow<CargaUiState> = _state.asStateFlow()
    var usuarios: List<Usuario> = emptyList()
    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    fun cargarLogin(nav: NavController) {
        viewModelScope.launch {
            usuarios = obtenerUsuario()
            if (usuarios.isNullOrEmpty()) {
                nav.navigate(Pantalla.Bienvenida.ruta) { popUpTo(0) }
            } else {
                var usuario: Usuario = usuarios.component1()
                UsuarioSession.iniciarLogin(usuario.rol,usuario.correo)
                try {
                    _state.update { it.copy(isCreating = true, createError = null) }

                    val nuevoPost = LoginPost(
                        correo = usuario.correo,
                        contrasenia = usuario.contrasenia
                    )

                    val creado = api.loginUsuario(nuevoPost)
                    if (creado.mensaje.equals("ok")) {
                        _state.update {
                            it.copy(
                                created = creado, isCreating = false, loginState = true)
                        }
                        nav.navigate(Pantalla.Inicio.ruta)
                    } else {
                        _state.update { it.copy(isCreating = false, createError = creado.mensaje) }
                    }

                } catch (e: Exception) {
                    _state.update { it.copy(isCreating = false, createError = e.message) }
                }
            }
        }
    }
    suspend fun obtenerUsuario(): List<Usuario>{
        return service.obtenerUsuarios()
    }
}