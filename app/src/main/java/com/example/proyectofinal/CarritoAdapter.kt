package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(private val productos: List<Producto>) : BaseAdapter() {

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
        holder.bind(producto)

        // Agregar un margen inferior solo para el último elemento
        val params = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = params

        if (position == count - 1) {
            params.bottomMargin = view.context.resources.getDimensionPixelSize(R.dimen.margin_bottom_last_item)
        } else {
            params.bottomMargin = 0
        }

        return view
    }


    class ViewHolder(itemView: View) {
        private val nombreProducto: TextView = itemView.findViewById(R.id.nombreProductoCarrito)
        private val precioProducto: TextView = itemView.findViewById(R.id.precioProductoCarrito)

        fun bind(producto: Producto) {
            nombreProducto.text = producto.nombre
            precioProducto.text = "$ ${producto.precio}"
        }
    }
}