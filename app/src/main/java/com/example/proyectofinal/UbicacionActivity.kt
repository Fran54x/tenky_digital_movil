package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class UbicacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)

        val imgRegresar: ImageView = findViewById(R.id.imgRegresar)
        imgRegresar.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la actividad anterior (PrincipalActivity)
        }

        val imgDirigir: ImageView = findViewById(R.id.imgDirigir)
        imgDirigir.setOnClickListener {
            // Abre la actividad del carrito
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}