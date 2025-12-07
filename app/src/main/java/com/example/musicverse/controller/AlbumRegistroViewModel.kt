package com.example.musicverse.controller

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.api.ApiService
import com.example.musicverse.model.AlbumCrearPost
import com.example.musicverse.model.Genero
import com.example.musicverse.network.RetrofitProvider
import com.example.musicverse.service.UsuarioService
import com.example.musicverse.view.PantallaInicio
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

data class AlbumRegistroUiState(
    val nombre: String = "",
    val formato: String = "",
    val upc: String = "",
    val fecha: String = "",
    val precio: String = "",
    val stock: String = "",
    val artista: String = "",
    val genero: String = "",
    val imageUri: Uri? = null,
    var generos: List<Genero> = emptyList(),
    val created: String = "",
    val isCreating: Boolean = false,
    val createError: String? = null,
)
class AlbumRegistroViewModel (app: Application): AndroidViewModel(app){
    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val service = UsuarioService(app)
    private val _state = MutableStateFlow(AlbumRegistroUiState())
    val state: StateFlow<AlbumRegistroUiState> = _state.asStateFlow()
    private val context get() = getApplication<Application>().applicationContext

    init {
        obtenerGeneros()
    }

    fun registrarAlbum(nav: NavController) {
        val p = _state.value
        if (p.nombre.isBlank() || p.formato.isBlank() ||
            p.upc.isBlank() || p.fecha.isBlank() ||
            p.precio.isBlank() || p.stock.isBlank() ||
            p.artista.isBlank() || p.genero.isBlank()
        ) {
            _state.update {
                it.copy(createError = "Todos los campos son obligatorios")
            }
            return
        }

        if (p.imageUri == null) {
            _state.update { it.copy(createError = "Debes seleccionar una imagen") }
            return
        }

        val upcLong = p.upc.toLongOrNull()
        if (upcLong == null || upcLong <= 0) {
            _state.update { it.copy(createError = "UPC inválido") }
            return
        }

        val precioInt = p.precio.toIntOrNull()
        if (precioInt == null || precioInt <= 0) {
            _state.update { it.copy(createError = "Precio inválido") }
            return
        }

        val stockInt = p.stock.toIntOrNull()
        if (stockInt == null || stockInt < 0) {
            _state.update { it.copy(createError = "Stock inválido") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isCreating = true, createError = null) }
            try {
                val dto = AlbumCrearPost(
                    nombre = p.nombre,
                    formato = p.formato,
                    codeUPC = p.upc.toLong(),
                    fecha_lanza = p.fecha,
                    precio = p.precio.toInt(),
                    stock = p.stock.toInt(),
                    artista = p.artista,
                    genero = p.genero
                )
                val dtoJson = Gson().toJson(dto)
                val jsonPart = MultipartBody.Part.createFormData(
                    "data",
                    null, // nombre de archivo no es necesario
                    dtoJson.toRequestBody("application/json".toMediaType())
                )
                val mimeType = context.contentResolver.getType(p.imageUri) ?: "image/png"
                val bytes = context.contentResolver.openInputStream(p.imageUri)!!.readBytes()
                val imageBody = bytes.toRequestBody(mimeType.toMediaType())
                val imagenPart = MultipartBody.Part.createFormData(
                    "imagen",
                    "album_${System.currentTimeMillis()}.png",
                    imageBody
                )

                api.registrarAlbum(data = jsonPart, imagen = imagenPart)
                nav.navigate(Pantalla.Inicio.ruta)
            } catch (e: Exception) {
                println(e)
                _state.update {
                    it.copy(isCreating = false, createError = e.message)
                }
            }
        }
    }

    fun onNombreChange(value: String) {
        _state.update { it.copy(nombre = value, createError = null) }
    }
    fun onFormatoChange(value: String){
        _state.update { it.copy(formato = value, createError = null) }
    }
    fun onUpcChange(value: String) {
        _state.update { it.copy(upc = value, createError = null) }
    }
    fun onPrecioChange(value: String){
        _state.update { it.copy(precio = value, createError = null) }
    }
    fun onFechaChange(value: String){
        _state.update { it.copy(fecha = value, createError = null) }
    }
    fun onStockChange(value: String){
        _state.update { it.copy(stock = value, createError = null) }
    }
    fun onArtistaChange(value: String) {
        _state.update { it.copy(artista = value, createError = null) }
    }
    fun onGeneroChange(value: String) {
        _state.update { it.copy(genero = value, createError = null) }
    }
    fun onImageChange(uri: Uri?) {
        _state.update { it.copy(imageUri = uri, createError = null) }
    }

    fun obtenerGeneros(){
        val p = _state.value
        viewModelScope.launch {
            var generos = api.obtenerGeneros()
            p.generos = generos
        }
    }
}