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
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class CitaActivity:  AppCompatActivity(), SensorEventListener {

    private lateinit var nombre: EditText
    private lateinit var telefono: EditText
    private lateinit var equipo: EditText
    private lateinit var fecha: EditText
    private lateinit var correo: EditText
    private lateinit var enviarBtn: AppCompatButton
    // val citaArray = Array<Cita?>(100){null}

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }


        nombre = findViewById(R.id.edtNombre)
        telefono = findViewById(R.id.edtTelefono)
        equipo = findViewById(R.id.edtEquipo)
        correo = findViewById(R.id.edtCorreo)
        fecha = findViewById(R.id.edtFecha)
        enviarBtn = findViewById(R.id.btnEnviar)

        val imgRegreso: ImageView = findViewById(R.id.imgextra3)
        imgRegreso.setOnClickListener {
            finish()
        }

        val imgCitaAdmin: ImageView = findViewById(R.id.imgCitasAdmin)
        imgCitaAdmin.setOnClickListener {
            IrCita()
        }

        enviarBtn.setOnClickListener{AgregarCita()}

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
        TODO("Not yet implemented")
    }


    private fun IrCita(){
        val intent = Intent(this, CitasAdmin::class.java)
        startActivity(intent)
    }

    private fun AgregarCita(){

        val nombreStr = nombre.text.toString()
        val telefonoInt = telefono.text.toString().toIntOrNull()
        val equipoStr = equipo.text.toString()
        val fechaStr = fecha.text.toString()
        val correoStr = correo.text.toString()

        if (nombreStr.isEmpty() || telefonoInt == null || equipoStr.isEmpty() || fechaStr.isEmpty() || correoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val dataHolder = DataHolder

        if (dataHolder.citaArray == null) {
            dataHolder.citaArray = Array<Cita?>(100) { null }
        }


        if (dataHolder.citaArray?.any { it?.fecha == fechaStr } == true) {
            Toast.makeText(this, "Fecha no disponible, por favor, elija otra", Toast.LENGTH_SHORT).show()
            return
        }

        val index = dataHolder.citaArray?.indexOfFirst { it == null } ?: -1

        if (index == -1) {
            Toast.makeText(this, "No hay espacio para más citas", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevaCita = Cita(nombreStr, telefonoInt, equipoStr, fechaStr, correoStr)

        // Actualizar el array en DataHolder
        dataHolder.citaArray?.set(index, nuevaCita)

        nombre.text.clear()
        telefono.text.clear()
        equipo.text.clear()
        fecha.text.clear()
        correo.text.clear()

        Toast.makeText(this, "Cita agregada con éxito.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
    }

}