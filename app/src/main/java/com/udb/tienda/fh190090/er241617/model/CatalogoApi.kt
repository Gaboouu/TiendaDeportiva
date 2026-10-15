package com.udb.tienda.fh190090.er241617.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatalogoApi {

    @GET("products")
    fun obtenerProductos(@Query("limit") limite: Int): Call<ProductosResponse>

    @GET("products/search")
    fun buscarProductos(@Query("q") texto: String): Call<ProductosResponse>
}
