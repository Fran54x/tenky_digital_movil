package com.example.proyectofinal

import android.content.Intent
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

        adapter.setOnItemClickListener(object : ProductoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val productoSeleccionado = productos[position]
                val intent = Intent(this@ProductoIndividualActivity, DetalleActivity::class.java)
                intent.putExtra("producto", productoSeleccionado)
                startActivity(intent)
            }
        })
    }

    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto(R.drawable.p1, "Impresora 1", 100.0, "Descripción de la impresora 1"),
            Producto(R.drawable.p2, "Producto 2", 150.0, "Descripción del producto 2"),
        )
    }
}