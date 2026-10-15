package com.udb.tienda.fh190090.er241617.model

data class ResumenInventario(
    val totalProductos: Int,
    val valorInventario: Double,
    val stockBajo: List<Producto>
)
