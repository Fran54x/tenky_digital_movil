package com.example.proyectofinal

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        // Intenta obtener el Producto de los extras del Intent
        val producto: Producto? = intent.getParcelableExtra("producto")

        if (producto != null) {
            val btnAgregar: Button = findViewById(R.id.btnAgregar)
            btnAgregar.setOnClickListener {
                Carrito.addProducto(producto)
                Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Manejar el caso en el que el extra es nulo
            Toast.makeText(this, "No se pudo obtener el producto", Toast.LENGTH_SHORT).show()
            // Puedes finalizar la actividad o realizar alguna otra acción
            finish()
        }
    }
}
