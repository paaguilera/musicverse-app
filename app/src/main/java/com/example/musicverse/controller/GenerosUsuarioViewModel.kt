package com.example.musicverse.controller

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicverse.api.ApiService
import com.example.musicverse.model.GenerosPost
import com.example.musicverse.model.RegistroPost
import com.example.musicverse.network.RetrofitProvider
import com.example.musicverse.service.UsuarioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InteresesUiState(
    // creación
    val rut: String = "",
    val isCreating: Boolean = false,
    val created: GenerosPost? = null,
    val createError: String? = null,
    // listado
    val list: List<GenerosPost> = emptyList(),
    val isListLoading: Boolean = false,
    val listError: String? = null
)

class GenerosUsuarioViewModel(app: Application) : AndroidViewModel(app) {
    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val _state = MutableStateFlow(InteresesUiState())

    private val service = UsuarioService(app)

    val state: StateFlow<InteresesUiState> = _state.asStateFlow()

    fun onRutChange(value: String) {
        _state.update { it.copy(rut = value, createError = null) }
    }
    fun crearGenerosUsuario(generos: List<Int>, rut: String){
        val p = _state.value

        viewModelScope.launch {
            try {
                _state.update { it.copy(isCreating = true, createError = null) }

                val nuevoPost = GenerosPost(
                    generosIds = generos
                )
                val creado = api.registrarGenerosUsuario(rut,nuevoPost)
                _state.update { it.copy(created = creado, isCreating = false) }

            }catch (e: Exception){
                _state.update { it.copy(isCreating = false, createError = e.message) }
            }
        }
    }

    suspend fun obtenerGeneros(rut: String): List<Int>{
        var generos = api.obtenerGenerosUser(rut)

        var ids: MutableList<Int> = mutableListOf<Int>()
        generos.forEach { generoDTO ->
            ids.add(generoDTO.id)
        }
        return ids;
    }
}