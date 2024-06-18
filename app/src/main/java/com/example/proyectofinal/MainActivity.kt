package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }

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