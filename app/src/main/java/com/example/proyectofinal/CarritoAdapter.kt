package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(private val productos: List<Producto>) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreProducto: TextView = itemView.findViewById(R.id.nombreProductoCarrito)
        private val precioProducto: TextView = itemView.findViewById(R.id.precioProductoCarrito)

        fun bind(producto: Producto) {
            nombreProducto.text = producto.nombre
            precioProducto.text = "$ ${producto.precio}"
        }
    }
}