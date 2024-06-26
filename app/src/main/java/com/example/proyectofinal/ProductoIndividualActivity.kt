package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductoIndividualActivity : AppCompatActivity(),SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productoindividual)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val productos = obtenerListaDeProductos()
        val adapter = ProductoAdapter(productos)
        recyclerView.adapter = adapter
        val btnRegreso: Button = findViewById(R.id.btnRegreso)
        btnRegreso.setOnClickListener {
            finish()
        }
        adapter.setOnItemClickListener(object : ProductoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val productoSeleccionado = productos[position]
                val intent = Intent(this@ProductoIndividualActivity, DetalleActivity::class.java)
                intent.putExtra("producto", productoSeleccionado)
                startActivity(intent)
            }
        })
    }

    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto(R.drawable.p1, "Canon IR2200", 10000.00, "Copiadora e impresora Blanco y negro, cuenta con escaner a color resolucion hasta 600x600 pixeles, capacidad de escaneo hasta 13x18 pulgadas."),
            Producto(R.drawable.p2, "Ricoh MP40", 6000.00, "Copiadora e impresora color laser, cuenta con escaner a color resolucion hasta 800x800 pixeles, capacidad de escaneo hasta 11x17 pulgadas o doble carta."),
            Producto(R.drawable.p3, "Canon IR4020", 14599.99, "Copiadora e impresora Blanco y negro, cuenta con escaner a color resolucion hasta 800x800 pixeles, capacidad de escaneo hasta 11x17 pulgadas o doble carta, con capacidad de hasta 4000 copias por hora."),
            Producto(R.drawable.p4, "Xerox Docucolor 2035", 40499.99, "Copiadora e impresora a color laser, cuenta con escaner a color resolucion hasta 600x600 pixeles, capacidad de escaneo hasta 13x18 pulgadas con unos colores muy vivos y puros."),
            Producto(R.drawable.p5, "Kyocera VN3025", 4200.00, "Copiadora e impresora Blanco y negro, cuenta con escaner a color resolucion hasta 800x800 pixeles, capacidad de escaneo hasta 11x17 pulgadas o doble carta."),
            Producto(R.drawable.p6, "Real Madrid", 14000.00, "El mejor club en la historia de futbol siendo el mas ganador de la maxima competicion europea y de mayor prestigio la UEFA Champions League con un total de 14."),
        )
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