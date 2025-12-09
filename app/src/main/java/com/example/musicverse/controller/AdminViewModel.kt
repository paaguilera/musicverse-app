package com.example.musicverse.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicverse.api.ApiService
import com.example.musicverse.model.EstadoAlbum
import com.example.musicverse.model.UsuarioMini
import com.example.musicverse.network.RetrofitProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminUiState(
    val usuariosMini: List<UsuarioMini> = emptyList(),
    val buscador: String = "",
    val created: String = "",
    val isCreating: Boolean = false,
    val createError: String? = null
        )
class AdminViewModel(app: Application): AndroidViewModel(app){
    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val _state = MutableStateFlow(AdminUiState())
    val state: StateFlow<AdminUiState> = _state.asStateFlow()

    fun onBuscadorChange(value: String) {
        _state.update { it.copy(buscador = value) }
    }

    fun listaUsuarios(){
        val p = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isCreating = true, createError = null) }
            val listaUsuarios = mutableListOf<UsuarioMini>()
            listaUsuarios.addAll(api.usuariosListaMini())
            _state.update { it.copy(isCreating = false, createError = null, usuariosMini = listaUsuarios) }
        }
    }
    fun buscarUsuarios(query: String){
        val p = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isCreating = true, createError = null) }
            val listaUsuarios = mutableListOf<UsuarioMini>()
            listaUsuarios.addAll(api.buscarUsers(query))
            _state.update { it.copy(isCreating = false, createError = null, usuariosMini = listaUsuarios) }
        }
    }
    fun borrarUsuario(rut: String){
        val p = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isCreating = true, createError = null) }
            try {
                api.usuarioBorrar(rut)
            }catch (e: Exception){
                _state.update { it.copy(isCreating = false, createError = e.message) }
            }
            listaUsuarios()
            _state.update { it.copy(isCreating = false, createError = null) }
        }
    }
    fun estadoAlbum(id: Int, estado: Boolean){
        val p = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isCreating = true, createError = null) }
            try {
                var estadoAlbum = EstadoAlbum(
                    estado = estado
                )
                api.cambiarEstadoAlbum(id,estadoAlbum)
            }catch (e: Exception){
                _state.update { it.copy(isCreating = false, createError = e.message) }
            }
            _state.update { it.copy(isCreating = false, createError = null) }
        }
    }
}