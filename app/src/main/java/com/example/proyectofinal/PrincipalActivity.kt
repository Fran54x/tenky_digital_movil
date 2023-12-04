package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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

        val imgCarrito: ImageView = findViewById(R.id.imgCarrito)
        val txtVer: TextView = findViewById(R.id.txtVer)
        txtVer.setOnClickListener {
            // Abrir Activity_ProductoIndividual al hacer clic en el TextView
            val intent = Intent(this, ProductoIndividualActivity::class.java)
            startActivity(intent)
        }
        imgCarrito.setOnClickListener {
            // Abre la actividad del carrito
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
    }

    private val onItemClickListener: (Producto) -> Unit = { producto ->
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("producto", producto)
        startActivity(intent)
    }

    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto(R.drawable.p1, "Producto 1", 19.99, "Descripción del Producto 1"),
            Producto(R.drawable.p2, "Producto 2", 29.99, "Descripción del Producto 2"),
            // Agrega más productos según sea necesario
        )
    }
}