package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val edtCorreo = findViewById<EditText>(R.id.edtCorreo)
        val edtContrasena = findViewById<EditText>(R.id.edtContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegistrr = findViewById<Button>(R.id.btnRegistrate)

        btnLogin.setOnClickListener {
            val correo = edtCorreo.text.toString()
            val contrasena = edtContrasena.text.toString()

            val db = Firebase.firestore
            db.collection("usuarios")
                .whereEqualTo("correo", correo)
                .whereEqualTo("contrasena", contrasena)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        Toast.makeText(this, "Usuario no encontrado o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    } else {
                        for (document in documents) {
                            val usuario = document.toObject(Usuario::class.java)
                            val intent = Intent(this, PrincipalActivity::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al buscar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        btnRegistrr.setOnClickListener {
            val intent = Intent(this, ActivityRegistro::class.java)
            startActivity(intent)
        }
    }
}