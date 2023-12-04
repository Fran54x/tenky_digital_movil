package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PrincipalActivity : AppCompatActivity() {

    private lateinit var listaProductos: List<Producto>  // Agregada la referencia a la lista de productos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val rvCatalogo: RecyclerView = findViewById(R.id.rvCatalogo)
        rvCatalogo.layoutManager = LinearLayoutManager(this)

        listaProductos = obtenerListaDeProductos()  // Inicializada la lista de productos

        val adapter = CatalogoAdapter(listaProductos, onItemClickListener)
        rvCatalogo.adapter = adapter

        val imgCarrito: ImageView = findViewById(R.id.imgCarrito)

        imgCarrito.setOnClickListener {
            // Abre la actividad del carrito
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
        val txtVer: TextView = findViewById(R.id.txtVer)
        txtVer.setOnClickListener {
            // Abrir Activity_ProductoIndividual al hacer clic en el TextView
            val intent = Intent(this, ProductoIndividualActivity::class.java)
            startActivity(intent)
        }

        val imgInfo: ImageView = findViewById(R.id.imgInfo)
        imgInfo.setOnClickListener {
            // Abre la actividad del carrito
            val intent = Intent(this, UbicacionActivity::class.java)
            startActivity(intent)
        }
        val edtBuscar: EditText = findViewById(R.id.edtBuscar)
        edtBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar nada aquí
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Filtrar la lista de productos según el texto de búsqueda
                val productosFiltrados = filtrarProductos(charSequence.toString())
                // Actualizar el adaptador con la nueva lista filtrada
                adapter.actualizarLista(productosFiltrados)
            }

            override fun afterTextChanged(editable: Editable?) {
                // No es necesario implementar nada aquí
            }
        })
    }

    private val onItemClickListener: (Producto) -> Unit = { producto ->
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("producto", producto)
        startActivity(intent)
    }

    private fun filtrarProductos(textoBusqueda: String): List<Producto> {
        return listaProductos.filter { producto ->
            producto.nombre.contains(textoBusqueda, ignoreCase = true)
        }
    }

    private fun obtenerListaDeProductos(): List<Producto> {
        return listOf(
            Producto(R.drawable.p1, "Producto 1", 19.99, "Descripción del Producto 1"),
            Producto(R.drawable.p2, "Producto 2", 29.99, "Descripción del Producto 2"),
        )
    }
}