package com.example.musicverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.musicverse.controller.CarritoManagerViewModel
import com.example.musicverse.data.UsuarioSession
import com.example.musicverse.view.AplicacionMusicVerse


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AplicacionMusicVerse() }
    }
}

// Global para mantener el estado del carrito
val carritoManagerViewModel = CarritoManagerViewModel()
sealed class Pantalla(val ruta: String) {
    object Carga : Pantalla("carga")
    object CargaGeneral : Pantalla("cargaGeneral")
    object Bienvenida : Pantalla("bienvenida")
    object Registro : Pantalla("registro")
    object InicioSesion : Pantalla("inicio_sesion")
    object Restablecer : Pantalla("restablecer")
    object CorreoEnviado : Pantalla("correo_enviado")
    object AlbumRegistro : Pantalla("album-registro")
    object Intereses : Pantalla("intereses/{origen}/{rut}"){
        fun crearRutaInteres(rut: String,origen: Int) = "intereses/$origen/$rut"
    }
    object Inicio : Pantalla("inicio")
    object ModificarUsuario: Pantalla("modificar-usuario")
    object Detalle : Pantalla("detalle/{album}") {
        fun crear(album: String) = "detalle/$album"
    }
    object Carrito : Pantalla("carrito/{album}") {
        fun crear(album: String) = "carrito/$album"
    }
    object ConfirmarPedido : Pantalla("confirmar_pedido")
    object CompraFinalizada : Pantalla("compra_finalizada")
}
