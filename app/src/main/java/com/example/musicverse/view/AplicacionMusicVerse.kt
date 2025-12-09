package com.example.musicverse.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicverse.Pantalla

@Composable
fun AplicacionMusicVerse() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Pantalla.Carga.ruta) {
        composable(Pantalla.Carga.ruta) { PantallaCargaInicio(nav) }
        composable(Pantalla.CargaGeneral.ruta) { PantallaCargaGeneral(nav) }
        composable(Pantalla.Bienvenida.ruta) { PantallaBienvenida(nav) }
        composable(Pantalla.Registro.ruta) { PantallaRegistro(nav) }
        composable(Pantalla.InicioSesion.ruta) { PantallaInicioSesion(nav) }
        composable(Pantalla.Restablecer.ruta) { PantallaRestablecer(nav) }
        composable (Pantalla.AlbumRegistro.ruta) { PantallaRegistrarAlbum(nav) }
        composable(Pantalla.CorreoEnviado.ruta) { PantallaCorreoEnviado(nav) }
        composable ( Pantalla.AdminUsers.ruta ){ PantallaAdminUsuarios(nav) }
        composable(Pantalla.Admin.ruta) { PantallaAdmin(nav) }
        composable(
            Pantalla.Intereses.ruta,
            arguments = listOf(
                navArgument("rut") {defaultValue = ""},
                navArgument("origen") {defaultValue = 0}
            )
        )
        {
            backStackEntry ->
            val rut = backStackEntry.arguments?.getString("rut") ?: ""
            val origen = backStackEntry.arguments?.getInt("origen") ?: 0
            PantallaIntereses(nav, rut, origen)
        }
        composable(Pantalla.Inicio.ruta) { PantallaInicio(nav) }
        composable(Pantalla.ModificarUsuario.ruta ) { PantallaModificarUsuario(nav)}
        composable(Pantalla.Detalle.ruta, arguments = listOf(navArgument("album") { defaultValue = "" })) {
            val album = it.arguments?.getString("album") ?: ""
            PantallaDetalle(nav, album)
        }
        composable(Pantalla.Carrito.ruta) { PantallaCarrito(nav) }
        composable(Pantalla.ConfirmarPedido.ruta) {
            PantallaConfirmarPedido(
                nav
            )
        }
        composable(Pantalla.CompraFinalizada.ruta) {
            PantallaCompraFinalizada(
                nav
            )
        }
    }
}