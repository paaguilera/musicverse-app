package com.example.musicverse.controller

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.api.ApiService
import com.example.musicverse.model.Genero
import com.example.musicverse.network.RetrofitProvider
import com.example.musicverse.service.UsuarioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InicioUiState(
    val correo: String = "",
    val rut: String = "",
    val generos: List<Genero> = emptyList(),
    val buscador: String = ""
)
class InicioViewModel (app: Application): AndroidViewModel(app){

    private val _state = MutableStateFlow(InicioUiState())

    val state: StateFlow<InicioUiState> = _state.asStateFlow()

    private val service = UsuarioService(app)
    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val _correo = MutableLiveData<String>()
    val correo: LiveData<String> = _correo
    private val _rut = MutableLiveData<String>()
    val rut: LiveData<String> = _rut

    fun obtenerCorreoDAO(){
        viewModelScope.launch {
            val mail = service.obtenerCorreo()
            _state.update { it.copy(correo = mail) }
            _correo.postValue(mail)
        }
    }
    fun obtenerRutDAO(){
        viewModelScope.launch {
            val rut = service.obtenerRut()
            _state.update { it.copy(rut = rut) }
            _rut.postValue(rut)
        }
    }

    fun cerrarSession(nav: NavController){
        viewModelScope.launch {
            try {
                val response = api.logout()
                if (response.isSuccessful) {
                    service.truncarTabla()
                    Log.d("Logout", "Sesión cerrada correctamente")
                    nav.navigate(Pantalla.Carga.ruta)
                } else {
                    Log.e("Logout", "Error ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Logout", "Error al cerrar sesión: ${e.localizedMessage}")
            }
        }
    }

    fun obtenerGeneros(){
        val p = state.value
        viewModelScope.launch {
            var generos2 = mutableListOf<Genero>()
            generos2.add(Genero(nombre = "Todos", idGenero = -1))
            generos2.add(Genero(nombre = "Favoritos", idGenero = 0))
            var generos = api.obtenerGeneros()
            generos2.addAll(generos)
            _state.update { it.copy(generos = generos2) }
        }
    }
    fun onBuscadorChange(value: String) {
        _state.update { it.copy(buscador = value) }
    }
}