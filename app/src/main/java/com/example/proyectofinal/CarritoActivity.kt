package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoActivity : AppCompatActivity() {

    private lateinit var lvCarrito: ListView
    private lateinit var txtPago: TextView
    private lateinit var txtPago2: TextView
    private lateinit var txtPago3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        lvCarrito = findViewById(R.id.lvProductos)
        txtPago = findViewById(R.id.txtPago)
        txtPago2 = findViewById(R.id.txtPago2)
        txtPago3 = findViewById(R.id.txtPago3)

        val adapter = CarritoAdapter(Carrito.getProductos())
        lvCarrito.adapter = adapter

        val imgRegreso: ImageView = findViewById(R.id.imgextra2)
        imgRegreso.setOnClickListener {
            finish()
        }

        val btnPagar: Button = findViewById(R.id.btnPagar)
        btnPagar.setOnClickListener {
            Carrito.clearCarrito()

            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            finish()
        }
        actualizarTextos()
    }

    private fun actualizarTextos() {

        val subtotal = calcularSubtotal()
        txtPago.text = "$ $subtotal"
        val montoFijo = 100.00
        txtPago2.text = "$ $montoFijo"
        val total = subtotal + montoFijo
        txtPago3.text = "$ $total"
    }
    private fun calcularSubtotal(): Double {
        val productosEnCarrito = Carrito.getProductos()
        var subtotal = 0.0

        for (producto in productosEnCarrito) {
            subtotal += producto.precio
        }
        return subtotal
    }
}