package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class CitasAdmin : AppCompatActivity() {

    private lateinit var citasAdm: TextView
    private lateinit var returnBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas_admin)

        citasAdm = findViewById(R.id.txtArrayCitas)
        returnBtn = findViewById(R.id.btnReturnCita)



        val dataHolder = DataHolder
        val citaArray = dataHolder.citaArray

        if(citaArray != null){
            val citasTexto = citaArray.filterNotNull().joinToString("\n") {
                "Nombre: ${it.nombre}, Telefono: ${it.telefono}, Equipo: ${it.equipo}, Fecha: ${it.fecha}, Correo: ${it.correo}\n\n"

            }
            citasAdm.text = citasTexto
        } else {
            citasAdm.text = "No hay citas almacenadas"
        }

        returnBtn.setOnClickListener { Regresar() }



    }

    private fun Regresar(){
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
    }

}