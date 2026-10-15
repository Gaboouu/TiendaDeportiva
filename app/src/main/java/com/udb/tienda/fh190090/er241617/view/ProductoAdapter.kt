package com.udb.tienda.fh190090.er241617.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udb.tienda.fh190090.er241617.databinding.ItemProductoBinding
import com.udb.tienda.fh190090.er241617.model.Producto

class ProductoAdapter(
    private var productos: List<Producto>,
    private val onItemClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.binding.tvNombre.text = producto.nombre
        holder.binding.tvPrecio.text = producto.precio.comoDinero()
        holder.binding.tvStock.text = "Stock: ${producto.stock}"
        holder.binding.tvCategoria.text = producto.categoria
        holder.itemView.setOnClickListener { onItemClick(producto) }
    }

    override fun getItemCount(): Int = productos.size

    @SuppressLint("NotifyDataSetChanged")
    fun actualizar(nuevos: List<Producto>) {
        productos = nuevos
        notifyDataSetChanged()
    }
}
