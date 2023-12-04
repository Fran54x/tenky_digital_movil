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

        adapter.setOnItemClickListener(object : ProductoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Obtener el producto seleccionado
                val productoSeleccionado = productos[position]

                // Abrir DetalleActivity y pasar la información del producto
                val intent = Intent(this@ProductoIndividualActivity, DetalleActivity::class.java)
                intent.putExtra("producto", productoSeleccionado)
                startActivity(intent)
            }
        })
    }

    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto("Impresora 1", 100, R.drawable.impresora1),
            Producto("Producto 2", 150, R.drawable.producto2),
            // Agrega más productos según sea necesario
        )
    }
}