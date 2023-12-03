package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val rvCarrito: RecyclerView = findViewById(R.id.rvCarrito)
        rvCarrito.layoutManager = LinearLayoutManager(this)

        val adapter = CarritoAdapter(Carrito.getProductos())
        rvCarrito.adapter = adapter


        val btnPagar: Button = findViewById(R.id.btnPagar)
        btnPagar.setOnClickListener {

            Carrito.clearCarrito()

            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}