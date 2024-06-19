package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ActivityRegistro : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }


        auth = FirebaseAuth.getInstance()

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

            // Crear usuario en Firebase Authentication
            auth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso
                        val firebaseUser = auth.currentUser
                        if (firebaseUser != null) {
                            // Crear documento de usuario en Firestore
                            val nuevoUsuario = Usuario(nombre, telefono, correo, contrasena)
                            val db = Firebase.firestore
                            db.collection("usuarios").document(firebaseUser.uid)
                                .set(nuevoUsuario)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Error al registrar usuario en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        // Fallo en el registro
                        Toast.makeText(this, "Error al registrar usuario: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }//onCreate
    override fun onResume() {
        super.onResume()
        lightSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lightLevel = event.values[0]
            if (lightLevel < 10) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something if sensor accuracy changes
    }


}