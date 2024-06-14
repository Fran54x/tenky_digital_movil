package com.example.proyectofinal

import java.io.Serializable

data class Usuario(
    val nombre: String = "",
    val telefono: String = "",
    val correo: String = "",
    val contrasena: String = ""
): Serializable