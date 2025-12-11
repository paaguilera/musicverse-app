package com.example.musicverse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.musicverse.controller.CarritoManagerViewModel
import com.example.musicverse.model.ProductoGrand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*


@ExperimentalCoroutinesApi
class CarritoManagerViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: CarritoManagerViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CarritoManagerViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun addToCart_AñadeProducto() = runTest {
        val productoDePrueba = ProductoGrand(
            idAlbum = 1,
            nombre = "Test Album",
            formato = "CD",
            codeUPC = 123456789L,
            fecha_lanza = LocalDate.now(),
            precio = 1500,
            stock = 10,
            imagenUrl = "url/imagen.jpg",
            artista = "Test Artist",
            canciones = listOf("Song 1"),
            resenias = listOf("Great album!"),
            genero = "Rock",
            desabilidato = false
        )

        viewModel.addToCart(productoDePrueba)

        val cartItems = viewModel.cartItems.value
        assertNotNull(cartItems)
        assertEquals(1, cartItems.size)
        assertEquals("Test Album", cartItems[0].nombre)
        assertEquals(1, cartItems[0].cantidad)
        assertEquals(1, cartItems[0].albumId)
    }

    @Test
    fun addToCart_ProductoExistente() = runTest {
        val producto = ProductoGrand(
            1,
            "Test Album",
            "CD",
            123L,
            LocalDate.now(),
            1000,
            5,
            "url",
            "Artist",
            emptyList(),
            emptyList(),
            "Pop",
            false
        )
        viewModel.addToCart(producto)

        // Act
        viewModel.addToCart(producto)

        // Assert
        val cartItems = viewModel.cartItems.value
        assertEquals(1, cartItems.size)
        assertEquals(2, cartItems[0].cantidad)
    }

    @Test
    fun clearCart() = runTest {
        //debe vaciar la lista y actualizar state
        val producto = ProductoGrand(1,
            "Test Album",
            "CD",
            123L,
            LocalDate.now(),
            1000,
            5,
            "url",
            "Artist",
            emptyList(),
            emptyList(),
            "Pop",
            false)
        viewModel.addToCart(producto)

        viewModel.clearCart()

        // Assert
        val cartItems = viewModel.cartItems.value
        assertTrue(cartItems.isEmpty())
    }

}