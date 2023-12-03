package com.example.proyectofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductoIndividualActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productoindividual)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val productos = obtenerListaDeProductos()
        val adapter = ProductoAdapter(productos)
        recyclerView.adapter = adapter
    }
    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto(R.drawable.p1, "Impresora 1", 100.0, "Descripción 1"),
            Producto(R.drawable.p2, "Producto 2", 150.0, "Descripción 2"),

        )
    }
}