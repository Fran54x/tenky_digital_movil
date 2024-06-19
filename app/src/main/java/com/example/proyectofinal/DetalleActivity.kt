package com.example.proyectofinal

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class DetalleActivity : AppCompatActivity(),SensorEventListener {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }


        val producto: Producto? = intent.getParcelableExtra("producto")

        if (producto != null) {
            val imgDetalleproducto: ImageView = findViewById(R.id.imgDetalleproducto)
            imgDetalleproducto.setImageResource(producto.imagen)

            val txtTitulo1: TextView = findViewById(R.id.txtTitulo1)
            txtTitulo1.text = producto.nombre

            val txtCosto2: TextView = findViewById(R.id.txtCosto2)
            txtCosto2.text = "$${producto.precio}"

            val txtDescripcion: TextView = findViewById(R.id.txtDescripcion)
            txtDescripcion.text = producto.descripcion

            val imgRegreso: ImageView = findViewById(R.id.imgRegreso)
            imgRegreso.setOnClickListener {
                finish()
            }
            val btnAgregar: Button = findViewById(R.id.btnAgregar)
            btnAgregar.setOnClickListener {
                val userId = auth.currentUser?.uid ?: return@setOnClickListener
                FirestoreUtils.agregarProductoAlCarritoFirestore(producto, userId,
                    onSuccess = {
                        Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { e ->
                        Toast.makeText(this, "Error al agregar producto al carrito: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        } else {
            Toast.makeText(this, "No se pudo obtener el producto", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

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