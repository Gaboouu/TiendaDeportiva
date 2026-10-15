package com.udb.tienda.fh190090.er241617.model

data class Producto(
    val id: String? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val stock: Int = 0,
    val categoria: String = ""
)
