package com.example.proyectofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // Obtener una referencia al RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvCatalogo)

        // Configurar el LinearLayoutManager
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Obtener la lista de productos (simulada)
        val productos = obtenerListaDeProductos()

        // Crear y configurar el adaptador
        val adapter = CatalogoAdapter(productos)
        recyclerView.adapter = adapter
    }

    // Método simulado para obtener una lista de productos
    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto("Impresora Laser X100", "Impresora láser monocromática", 249.99),
            Producto("Copiadora Multifuncional A200", "Copiadora multifuncional a color", 599.99),
            // Agrega más productos según sea necesario
        )
    }
}