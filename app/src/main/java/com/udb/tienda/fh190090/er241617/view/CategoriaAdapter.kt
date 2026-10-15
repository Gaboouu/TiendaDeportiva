package com.udb.tienda.fh190090.er241617.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udb.tienda.fh190090.er241617.databinding.ItemCategoriaBinding
import com.udb.tienda.fh190090.er241617.model.Categoria

class CategoriaAdapter(
    private var categorias: List<Categoria>,
    private val onItemClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    class CategoriaViewHolder(val binding: ItemCategoriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val binding = ItemCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.binding.tvNombre.text = categoria.nombre
        holder.binding.tvDescripcion.text = categoria.descripcion
        holder.itemView.setOnClickListener { onItemClick(categoria) }
    }

    override fun getItemCount(): Int = categorias.size

    @SuppressLint("NotifyDataSetChanged")
    fun actualizar(nuevas: List<Categoria>) {
        categorias = nuevas
        notifyDataSetChanged()
    }
}
