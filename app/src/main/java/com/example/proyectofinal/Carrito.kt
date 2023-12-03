package com.example.proyectofinal

object Carrito {
    private val productosEnCarrito = mutableListOf<Producto>()
    fun getProductos(): List<Producto> {
        return productosEnCarrito.toList()
    }
    fun addProducto(producto: Producto) {
        productosEnCarrito.add(producto)
    }
    fun clearCarrito() {
        productosEnCarrito.clear()
    }
}