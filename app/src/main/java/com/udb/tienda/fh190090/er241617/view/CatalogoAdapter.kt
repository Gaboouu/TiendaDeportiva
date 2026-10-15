package com.udb.tienda.fh190090.er241617.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udb.tienda.fh190090.er241617.databinding.ItemCatalogoBinding
import com.udb.tienda.fh190090.er241617.model.ProductoProveedor

class CatalogoAdapter(
    private var productos: List<ProductoProveedor>,
    private val onItemClick: (ProductoProveedor) -> Unit
) : RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>() {

    class CatalogoViewHolder(val binding: ItemCatalogoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogoViewHolder {
        val binding = ItemCatalogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogoViewHolder, position: Int) {
        val producto = productos[position]
        holder.binding.tvNombre.text = producto.nombre
        holder.binding.tvPrecio.text = producto.precio.comoDinero()
        holder.binding.tvCategoria.text = producto.categoria
        Picasso.get()
            .load(producto.imagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.binding.ivProducto)
        holder.itemView.setOnClickListener { onItemClick(producto) }
    }

    override fun getItemCount(): Int = productos.size

    @SuppressLint("NotifyDataSetChanged")
    fun actualizar(nuevos: List<ProductoProveedor>) {
        productos = nuevos
        notifyDataSetChanged()
    }
}
