package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val rvCarrito: RecyclerView = findViewById(R.id.rvCarrito)
        rvCarrito.layoutManager = LinearLayoutManager(this)

        // Configurar el espaciado entre elementos
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        rvCarrito.addItemDecoration(SpacesItemDecoration(spacingInPixels))

        val adapter = CarritoAdapter(Carrito.getProductos())
        rvCarrito.adapter = adapter

        val imgRegreso: ImageView = findViewById(R.id.imgextra2)
        imgRegreso.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la actividad anterior (PrincipalActivity)
        }

        val btnPagar: Button = findViewById(R.id.btnPagar)
        btnPagar.setOnClickListener {
            Carrito.clearCarrito()

            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Clase para configurar el espaciado entre elementos
    class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: android.graphics.Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space

            // Añade este bloque si no quieres espaciado en el primer elemento
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space
            }
        }
    }
}