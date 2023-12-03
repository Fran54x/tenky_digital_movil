package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val rvCatalogo: RecyclerView = findViewById(R.id.rvCatalogo)
        rvCatalogo.layoutManager = LinearLayoutManager(this)

        val productos = obtenerListaDeProductos()
        val adapter = CatalogoAdapter(productos, onItemClickListener)
        rvCatalogo.adapter = adapter
    }

    private val onItemClickListener: (Producto) -> Unit = { producto ->
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("producto", producto)
        startActivity(intent)
    }

    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto(R.drawable.producto1, "Producto 1", 19.99, "Descripción del Producto 1"),
            Producto(R.drawable.producto2, "Producto 2", 29.99, "Descripción del Producto 2"),
            // Agrega más productos según sea necesario
        )
    }
}