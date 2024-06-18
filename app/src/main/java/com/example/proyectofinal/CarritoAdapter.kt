package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class CarritoAdapter(private val productos: List<Producto>, private val onDelete: (Producto) -> Unit) : BaseAdapter() {

    override fun getCount(): Int {
        return productos.size
    }

    override fun getItem(position: Int): Any {
        return productos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_carrito, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val producto = getItem(position) as Producto
        holder.bind(producto, onDelete)

        return view
    }

    class ViewHolder(itemView: View) {
        private val nombreProducto: TextView = itemView.findViewById(R.id.nombreProductoCarrito)
        private val precioProducto: TextView = itemView.findViewById(R.id.precioProductoCarrito)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(producto: Producto, onDelete: (Producto) -> Unit) {
            nombreProducto.text = producto.nombre
            precioProducto.text = "$ ${producto.precio}"
            btnEliminar.setOnClickListener {
                onDelete(producto)
            }
        }
    }
}