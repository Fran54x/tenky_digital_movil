package com.example.proyectofinal

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class DetalleActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val producto: Producto? = intent.getParcelableExtra("producto")

        if (producto != null) {
            val imgDetalleproducto: ImageView = findViewById(R.id.imgDetalleproducto)
            imgDetalleproducto.setImageResource(producto.imagen)

            val txtTitulo1: TextView = findViewById(R.id.txtTitulo1)
            txtTitulo1.text = producto.nombre

            val txtCosto2: TextView = findViewById(R.id.txtCosto2)
            txtCosto2.text = "$${producto.precio}"

            val txtDescripcion: TextView = findViewById(R.id.txtDescripcion)
            txtDescripcion.text = producto.descripcion

            val imgRegreso: ImageView = findViewById(R.id.imgRegreso)
            imgRegreso.setOnClickListener {
                finish()
            }
            val btnAgregar: Button = findViewById(R.id.btnAgregar)
            btnAgregar.setOnClickListener {
                val userId = auth.currentUser?.uid ?: return@setOnClickListener
                FirestoreUtils.agregarProductoAlCarritoFirestore(producto, userId,
                    onSuccess = {
                        Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { e ->
                        Toast.makeText(this, "Error al agregar producto al carrito: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        } else {
            Toast.makeText(this, "No se pudo obtener el producto", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}