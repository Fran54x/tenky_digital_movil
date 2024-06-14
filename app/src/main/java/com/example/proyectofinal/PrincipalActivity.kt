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
    private var usuario: Usuario? = null
    private val txtSaludo: TextView? = null
    private val txtUsuario: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        usuario = intent.getSerializableExtra("usuario") as? Usuario
        usuario?.let {
            // Aquí puedes usar el objeto usuario, por ejemplo mostrar su nombre en un TextView
            val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
            txtUsuario.text = "Bienvenido, ${it.nombre}"
        }

        val rvCatalogo: RecyclerView = findViewById(R.id.rvCatalogo)
        rvCatalogo.layoutManager = LinearLayoutManager(this)

        listaProductos = obtenerListaDeProductos()  // Inicializada la lista de productos

        val adapter = CatalogoAdapter(listaProductos, onItemClickListener)
        rvCatalogo.adapter = adapter

        val imgCarrito: ImageView = findViewById(R.id.imgCarrito)
        val imgCalendario: ImageView = findViewById(R.id.imgCalendario)

        imgCalendario.setOnClickListener{
            val intent = Intent(this, CitaActivity::class.java)
            startActivity(intent)
        }

        imgCarrito.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
        val txtVer: TextView = findViewById(R.id.txtVer)
        txtVer.setOnClickListener {
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
            Producto(R.drawable.p1, "Canon IR2200", 10000.00, "Copiadora e impresora Blanco y negro, cuenta con escaner a color resolucion hasta 600x600 pixeles, capacidad de escaneo hasta 13x18 pulgadas"),
            Producto(R.drawable.p2, "Ricoh MP40", 6000.00, "Copiadora e impresora color laser, cuenta con escaner a color resolucion hasta 800x800 pixeles, capacidad de escaneo hasta 11x17 pulgadas o doble carta"),
            Producto(R.drawable.p3, "Canon IR4020", 14599.99, "Copiadora e impresora color laser, cuenta con escaner a color resolucion hasta 800x800 pixeles, capacidad de escaneo hasta 11x17 pulgadas o doble carta, con capacidad de hasta 4000 copias por hora"),
        )
    }
}