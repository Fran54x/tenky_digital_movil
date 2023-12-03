package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton



class CitaActivity:  AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var telefono: EditText
    private lateinit var equipo: EditText
    private lateinit var fecha: EditText
    private lateinit var correo: EditText
    private lateinit var enviarBtn: AppCompatButton
   // val citaArray = Array<Cita?>(100){null}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita)

        nombre = findViewById(R.id.edtNombre)
        telefono = findViewById(R.id.edtTelefono)
        equipo = findViewById(R.id.edtEquipo)
        correo = findViewById(R.id.edtCorreo)
        fecha = findViewById(R.id.edtFecha)
        enviarBtn = findViewById(R.id.btnEnviar)

        enviarBtn.setOnClickListener{AgregarCita()}

    }

    private fun AgregarCita(){

        val nombreStr = nombre.text.toString()
        val telefonoInt = nombre.text.toString().toIntOrNull()
        val equipoStr = equipo.text.toString()
        val fechaStr = fecha.text.toString()
        val correoStr = correo.text.toString()

        if (nombreStr.isEmpty() || telefonoInt == null || equipoStr.isEmpty() || fechaStr.isEmpty() || correoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val dataHolder = DataHolder

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