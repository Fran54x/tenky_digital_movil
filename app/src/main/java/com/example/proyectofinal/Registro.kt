package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class ActivityRegistro : AppCompatActivity() {

    private val listaUsuarios = mutableListOf<Usuario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtTelefono = findViewById<EditText>(R.id.edtTelefono)
        val edtCorreo = findViewById<EditText>(R.id.edtCorreo)
        val edtContrasena = findViewById<EditText>(R.id.edtContrasena)
        val btnRegistrar = findViewById<AppCompatButton>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = edtNombre.text.toString()
            val telefono = edtTelefono.text.toString()
            val correo = edtCorreo.text.toString()
            val contrasena = edtContrasena.text.toString()

            val nuevoUsuario = Usuario(nombre, telefono, correo, contrasena)

            listaUsuarios.add(nuevoUsuario)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("listaUsuarios", ArrayList(listaUsuarios))
            startActivity(intent)
            finish()
        }
    }
}