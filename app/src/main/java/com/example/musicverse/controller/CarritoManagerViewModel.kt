package com.example.musicverse.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.musicverse.api.ApiService
import com.example.musicverse.model.CarritoItem
import com.example.musicverse.model.ProductoGrand
import com.example.musicverse.network.RetrofitProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarritoManagerViewModel(){
    // El estado del carrito (lista de productos)
    private val _cartItems = MutableStateFlow<List<CarritoItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    fun addToCart(album: ProductoGrand?) {
        println(_cartItems.value)
        val current = _cartItems.value.toMutableList()
        println(current)
        if (album == null) return

        val existing = current.find { it.albumId == album.idAlbum }

        _cartItems.value =
            if (existing != null) {
                current.map {
                    if (it.albumId == album.idAlbum)
                        it.copy(cantidad = it.cantidad + 1)
                    else it
                }
            } else {
                current + CarritoItem(
                    albumId = album.idAlbum,
                    nombre = album.nombre,
                    artista = album.artista,
                    imagenUrl = album.imagenUrl,
                    precio = album.precio,
                    cantidad = 1
                )
            }
    }

    fun increaseQuantity(id: Int) {
        val current = _cartItems.value.map {
            if (it.albumId == id) it.copy(cantidad = it.cantidad + 1) else it
        }
        _cartItems.value = current
    }

    fun decreaseQuantity(id: Int) {
        val current = _cartItems.value.toMutableList()

        val item = current.find { it.albumId == id }

        if (item != null) {
            if (item.cantidad > 1) {
                item.cantidad--
            } else {
                current.remove(item)
            }
        }

        _cartItems.value = current
    }

    fun getTotal(): Int {
        return _cartItems.value.sumOf {
            it.cantidad * it.precio * (1 - it.desc)
        }.toInt()
    }

    fun removeFromCart(albumId: Int) {
        val updated = _cartItems.value.filter { it.albumId != albumId }
        _cartItems.value = updated
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}