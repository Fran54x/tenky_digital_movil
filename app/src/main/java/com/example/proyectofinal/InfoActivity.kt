package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val imgRegresar: ImageView = findViewById(R.id.imgRegresar2)
        imgRegresar.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la actividad anterior (PrincipalActivity)
        }
    }
}