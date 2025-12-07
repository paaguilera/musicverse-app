package com.example.musicverse.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicverse.api.ApiService
import com.example.musicverse.model.ProductoGrand
import com.example.musicverse.model.ProductoLista
import com.example.musicverse.network.RetrofitProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProductosUiState(
    val productosMini: List<ProductoLista> = emptyList(),
    val productoGrand: ProductoGrand? = null,
    val created: String = "",
    val isCreating: Boolean = false,
    val createError: String? = null,
)

class ProductosViewModel (app: Application): AndroidViewModel(app){

    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val _state = MutableStateFlow(ProductosUiState())

    val state: StateFlow<ProductosUiState> = _state.asStateFlow()

    fun obtenerProductosLista(id: Int, rut: String){
        val p = _state.value
        viewModelScope.launch {
            try {
                val listaGrande = mutableListOf<ProductoLista>()
                if(id == -1){
                    val lista = api.listaProductos()
                    listaGrande.addAll(lista)
                }else if(id == 0){
                    val lista = api.listaProductosUserFav(rut)
                    listaGrande.addAll(lista)
                }else{
                    val lista = api.listaProductosGenero(id)
                    listaGrande.addAll(lista)
                }
                _state.update { it.copy(productosMini = listaGrande) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun obtenerProductoNombre(nombre: String){
        val p = _state.value
        viewModelScope.launch {
            try {
                val producto = api.obtenerProductoNombre(nombre)
                _state.update { it.copy(productoGrand = producto) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun buscadorBoton(query: String){
        viewModelScope.launch {
            try {
                val lista = api.buscarAlbums(query = query)
                _state.update { it.copy(productosMini = lista) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}