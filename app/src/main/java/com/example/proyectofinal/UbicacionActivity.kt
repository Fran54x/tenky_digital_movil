package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbicacionActivity : AppCompatActivity(), OnMapReadyCallback { // Implementamos OnMapReadyCallback
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)

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
}