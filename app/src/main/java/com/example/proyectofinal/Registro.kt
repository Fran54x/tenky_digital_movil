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

        // Obtén las referencias a tus EditText y Button
        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtTelefono = findViewById<EditText>(R.id.edtTelefono)
        val edtCorreo = findViewById<EditText>(R.id.edtCorreo)
        val edtContrasena = findViewById<EditText>(R.id.edtContrasena)
        val btnRegistrar = findViewById<AppCompatButton>(R.id.btnRegistrar)

        // Maneja el evento de clic en el botón Registrar
        btnRegistrar.setOnClickListener {
            val nombre = edtNombre.text.toString()
            val telefono = edtTelefono.text.toString()
            val correo = edtCorreo.text.toString()
            val contrasena = edtContrasena.text.toString()

            // Crea un nuevo usuario
            val nuevoUsuario = Usuario(nombre, telefono, correo, contrasena)

            // Agrega el usuario a la lista
            listaUsuarios.add(nuevoUsuario)

            // Pasa la lista de usuarios a la ActivityMain y vuelve a ella
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("listaUsuarios", ArrayList(listaUsuarios))
            startActivity(intent)
            finish()
        }
    }
}