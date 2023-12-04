package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CatalogoAdapter(
    private var listaProductos: List<Producto>,
    private val onItemClickListener: (Producto) -> Unit
) : RecyclerView.Adapter<CatalogoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalogo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = listaProductos[position]  // Cambiado de productos a listaProductos
        holder.bind(producto)
        holder.itemView.setOnClickListener { onItemClickListener(producto) }
    }

    override fun getItemCount(): Int {
        return listaProductos.size  // Cambiado de productos a listaProductos
    }
    fun actualizarLista(nuevaLista: List<Producto>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        private val nombreProducto: TextView = itemView.findViewById(R.id.nombreProducto)
        private val precioProducto: TextView = itemView.findViewById(R.id.precioProducto)

        fun bind(producto: Producto) {
            imagenProducto.setImageResource(producto.imagen)
            nombreProducto.text = producto.nombre
            precioProducto.text = "$ ${producto.precio}"
        }
    }
}