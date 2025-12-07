package com.example.musicverse.api

import com.example.musicverse.dto.GeneroDTO
import com.example.musicverse.dto.ModificarDTO
import com.example.musicverse.model.Genero
import com.example.musicverse.model.GenerosPost
import com.example.musicverse.model.LoginGet
import com.example.musicverse.model.LoginPost
import com.example.musicverse.model.MetodosPagoGet
import com.example.musicverse.model.ProcesarCompra
import com.example.musicverse.model.ProductoGrand
import com.example.musicverse.model.ProductoLista
import com.example.musicverse.model.RegistroPost
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("registro")
    suspend fun registrarUsuario(@Body body: RegistroPost): Response<Unit>

    @Headers("Content-Type: application/json")
    @POST("/usuario/generos/{rut}")
    suspend fun registrarGenerosUsuario(@Path("rut") rut: String, @Body body: GenerosPost): GenerosPost

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun loginUsuario(@Body body: LoginPost): LoginGet

    @Headers("Content-Type: application/json")
    @PUT("usuario/modificar/{rut}")
    suspend fun updateUser(@Path("rut") rut: String, @Body body: ModificarDTO): Response<Unit>

    @Headers("Content-Type: application/json")
    @GET("usuario/{rut}/generos")
    suspend fun obtenerGenerosUser(@Path("rut") rut: String): List<GeneroDTO>

    @Headers("Content-Type: application/json")
    @POST("logout")
    suspend fun logout(): Response<Void>

    @Headers("Content-Type: application/json")
    @GET("album/mini")
    suspend fun listaProductos(): List<ProductoLista>

    @Headers("Content-Type: application/json")
    @GET("album/mini/{id}")
    suspend fun listaProductosGenero(@Path("id") id: Int): List<ProductoLista>
    @Headers("Content-Type: application/json")
    @GET("album/mini/user/{rut}")
    suspend fun listaProductosUserFav(@Path("rut") id: String): List<ProductoLista>
    @Headers("Content-Type: application/json")
    @GET("album/buscar/{nombre}")
    suspend fun obtenerProductoNombre(@Path("nombre") nombre: String): ProductoGrand

    @GET("album/buscador/mini")
    suspend fun buscarAlbums(@Query("query") query: String): List<ProductoLista>
    @Headers("Content-Type: application/json")
    @GET("metodopago")
    suspend fun obtenerMetodosPago(): List<MetodosPagoGet>

    @Headers("Content-Type: application/json")
    @POST("venta/comprar")
    suspend fun procesarCompra(@Body body: ProcesarCompra): Response<Unit>

    @Headers("Content-Type: application/json")
    @GET("generos")
    suspend fun obtenerGeneros(): List<Genero>

    @Multipart
    @POST("album/registrar")
    suspend fun registrarAlbum(
        @Part data: MultipartBody.Part,
        @Part imagen: MultipartBody.Part
    )
}