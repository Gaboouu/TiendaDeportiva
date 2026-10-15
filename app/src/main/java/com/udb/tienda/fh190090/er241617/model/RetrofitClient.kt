package com.udb.tienda.fh190090.er241617.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val URL_CATALOGO = "https://dummyjson.com/"

    const val URL_INVENTARIO = "https://6acebe6af338359cf1c3a6c8.mockapi.io/"

    private val clienteHttp: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private fun crearRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val catalogoApi: CatalogoApi by lazy {
        crearRetrofit(URL_CATALOGO).create(CatalogoApi::class.java)
    }

    val inventarioApi: InventarioApi by lazy {
        crearRetrofit(URL_INVENTARIO).create(InventarioApi::class.java)
    }
}
