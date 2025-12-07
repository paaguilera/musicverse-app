package com.example.musicverse.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.musicverse.Pantalla
import com.example.musicverse.api.ApiService
import com.example.musicverse.carritoManagerViewModel
import com.example.musicverse.model.MetodosPagoGet
import com.example.musicverse.model.ProcesarCompra
import com.example.musicverse.model.ProcesarCompraProd
import com.example.musicverse.model.QrJsonRespuesta
import com.example.musicverse.model.Usuario
import com.example.musicverse.network.RetrofitProvider
import com.example.musicverse.service.UsuarioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class ConfPedidoUiState(
    // creación
    val rut: String = "",
    val nombre: String = "",
    val correo: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val metodoPago: String = "",
    val metodoPagoInt: Int = 0,
    val desc: Int = 0,
    val qrrecibido: String = "",

    val created: String = "",
    val isCreating: Boolean = false,
    val createError: String? = null,
    // listado
    var metodosPago: List<MetodosPagoGet> = emptyList(),
    val isListLoading: Boolean = false,
    val listError: String? = null
)

class ConfirmarPedidoViewModel (app: Application): AndroidViewModel(app){
    private val api: ApiService by lazy { RetrofitProvider.create<ApiService>() }
    private val service = UsuarioService(app)
    private val _state = MutableStateFlow(ConfPedidoUiState())

    val state: StateFlow<ConfPedidoUiState> = _state.asStateFlow()

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
                telefono = usuario.telefono,
                rut = usuario.rut,
                metodoPagoInt = usuario.metodoPago,
            ) }
            cargarMetodoPago()
        }
    }

    suspend fun obtenerUsuarios(): List<Usuario>{
        return service.obtenerUsuarios()
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

    fun procesarCompra(navController: NavController){
        val p = _state.value
        viewModelScope.launch {
            val items = carritoManagerViewModel.cartItems.value
            val itemsPost = mutableListOf<ProcesarCompraProd>()
            items.forEach { item ->
                val nuevoProd = ProcesarCompraProd(
                    cantidad = item.cantidad,
                    desc = item.desc,
                    idAlbum = item.albumId
                )
                itemsPost.add(nuevoProd)
            }
            val nuevoPost = ProcesarCompra(
                idMetodoPago = p.metodoPagoInt,
                rutUsuario = p.rut,
                productos = itemsPost
            )
            val ok = api.procesarCompra(nuevoPost)
            if(ok.isSuccessful){
                navController.navigate(Pantalla.CompraFinalizada.ruta)
            }
        }
    }

    fun recibirQRDesc(qrValue: String): QrJsonRespuesta? {
        try {
            if (qrValue.isBlank()) return null
            val partes = qrValue.split(";")
            if (partes.size != 2) return null

            val idPart = partes[0].trim()
            val descPart = partes[1].trim()

            if (!idPart.startsWith("ID=")) return null
            if (!descPart.startsWith("DESC=")) return null

            val id = idPart.substringAfter("ID=").toIntOrNull() ?: return null
            val desc = descPart.substringAfter("DESC=").toDoubleOrNull() ?: return null

            if (id <= 0) return null
            if (desc <= 0.0 || desc > 1) return null

            return QrJsonRespuesta(desc,id)
        }catch (e: Exception){
            return null
        }
    }
    fun aplicarDescuentoAProducto(productoId: Int, descuento: Double) {
        val items = carritoManagerViewModel.cartItems.value
        items.forEach { item ->
            if(item.albumId == productoId&&descuento>0.0){
                item.desc = descuento;
            }else{
                item
            }
        }
    }
}
