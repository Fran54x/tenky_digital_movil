package com.example.proyectofinal

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        // Obtener el Producto de los extras del Intent
        val producto: Producto? = intent.getParcelableExtra("producto")

        if (producto != null) {
            // Actualizar las vistas con la información del producto
            val imgDetalleproducto: ImageView = findViewById(R.id.imgDetalleproducto)
            imgDetalleproducto.setImageResource(producto.imagen)

            val txtTitulo1: TextView = findViewById(R.id.txtTitulo1)
            txtTitulo1.text = producto.nombre

            val txtCosto2: TextView = findViewById(R.id.txtCosto2)
            txtCosto2.text = "$${producto.precio}"

            val txtDescripcion: TextView = findViewById(R.id.txtDescripcion)
            txtDescripcion.text = producto.descripcion

            // Resto de la lógica...
            val imgRegreso: ImageView = findViewById(R.id.imgRegreso)
            imgRegreso.setOnClickListener {
                finish() // Cierra la actividad actual y regresa a la actividad anterior (PrincipalActivity)
            }
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
