package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CarritoActivity : AppCompatActivity() {

    private lateinit var lvCarrito: ListView
    private lateinit var txtPago: TextView
    private lateinit var txtPago2: TextView
    private lateinit var txtPago3: TextView
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        lvCarrito = findViewById(R.id.lvProductos)
        txtPago = findViewById(R.id.txtPago)
        txtPago2 = findViewById(R.id.txtPago2)
        txtPago3 = findViewById(R.id.txtPago3)

        val imgRegreso: ImageView = findViewById(R.id.imgextra2)
        imgRegreso.setOnClickListener {
            finish()
        }

        val btnPagar: Button = findViewById(R.id.btnPagar)
        btnPagar.setOnClickListener {
            val userId = auth.currentUser?.uid ?: return@setOnClickListener
            FirestoreUtils.limpiarCarritoFirestore(userId,
                onSuccess = {
                    val intent = Intent(this, PrincipalActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                onFailure = { e ->
                    Toast.makeText(this, "Error al limpiar carrito: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        cargarProductosDelCarrito()
    }

    private fun cargarProductosDelCarrito() {
        val userId = auth.currentUser?.uid ?: return
        FirestoreUtils.obtenerProductosDelCarritoFirestore(userId,
            onSuccess = { productos ->
                val adapter = CarritoAdapter(productos)
                lvCarrito.adapter = adapter
                actualizarTextos(productos)
            },
            onFailure = { e ->
                Toast.makeText(this, "Error al obtener productos del carrito: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun actualizarTextos(productos: List<Producto>) {
        val subtotal = calcularSubtotal(productos)
        txtPago.text = "$ $subtotal"
        val montoFijo = 100.00
        txtPago2.text = "$ $montoFijo"
        val total = subtotal + montoFijo
        txtPago3.text = "$ $total"
    }

    private fun calcularSubtotal(productos: List<Producto>): Double {
        var subtotal = 0.0
        for (producto in productos) {
            subtotal += producto.precio
        }
        return subtotal
    }
}