package com.udb.tienda.fh190090.er241617.model

import com.google.gson.annotations.SerializedName


data class ProductosResponse(
    @SerializedName("products") val productos: List<ProductoProveedor> = emptyList(),
    @SerializedName("total") val total: Int = 0
)

data class ProductoProveedor(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val nombre: String = "",
    @SerializedName("description") val descripcion: String = "",
    @SerializedName("price") val precio: Double = 0.0,
    @SerializedName("category") val categoria: String = "",
    @SerializedName("thumbnail") val imagen: String = ""
)
