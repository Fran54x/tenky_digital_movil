package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val listaUsuarios = mutableListOf<Usuario>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listaUsuarios.add(Usuario("u1", "123456789", "u@ceti.com", "123"))

        val edtCorreo = findViewById<EditText>(R.id.edtCorreo)
        val edtContrasena = findViewById<EditText>(R.id.edtContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val correo = edtCorreo.text.toString()
            val contrasena = edtContrasena.text.toString()

            val usuarioEncontrado =
                listaUsuarios.find { it.correo == correo && it.contrasena == contrasena }

            if (usuarioEncontrado != null) {
                val intent = Intent(this, InfoActivity::class.java)
                intent.putExtra("usuario", usuarioEncontrado)
                startActivity(intent)
            } else {
                Toast.makeText(this,"Usuario no encontrado o contraseña incorrecta",Toast.LENGTH_SHORT).show()
            }
        }
    }
}