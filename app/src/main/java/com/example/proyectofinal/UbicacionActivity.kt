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
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbicacionActivity : AppCompatActivity(), OnMapReadyCallback, SensorEventListener { // Implementamos OnMapReadyCallback
    private lateinit var mMap: GoogleMap
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Agregar un marcador en Santa Teresita y mover la cámara
        val santaTeresita = LatLng(20.687206, -103.3727659) // Coordenadas aproximadas
        mMap.addMarker(MarkerOptions().position(santaTeresita).title("TENKI DIGITAL"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santaTeresita, 15f))
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