package com.udb.tienda.fh190090.er241617.model

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException

class ReporteRepository(private val cliente: OkHttpClient = OkHttpClient()) {

    private val tag = "ReporteRepository"

    @Throws(IOException::class)
    fun obtenerProductos(): List<Producto> {
        val peticion = Request.Builder()
            .url(RetrofitClient.URL_INVENTARIO + "producto")
            .get()
            .build()

        cliente.newCall(peticion).execute().use { respuesta ->
            if (!respuesta.isSuccessful) {
                Log.e(tag, "Petición fallida. Código HTTP: ${respuesta.code}")
                throw IOException("Código HTTP ${respuesta.code}")
            }
            val cuerpo = respuesta.body?.string() ?: throw IOException("Respuesta vacía")
            Log.d(tag, "Respuesta ${respuesta.code}: $cuerpo")
            return convertirJson(cuerpo)
        }
    }

    private fun convertirJson(json: String): List<Producto> {
        val arreglo = JSONArray(json)
        val productos = mutableListOf<Producto>()
        for (i in 0 until arreglo.length()) {
            val objeto = arreglo.getJSONObject(i)
            productos.add(
                Producto(
                    id = objeto.optString("id"),
                    nombre = objeto.optString("nombre"),
                    descripcion = objeto.optString("descripcion"),
                    precio = objeto.optDouble("precio", 0.0),
                    stock = objeto.optInt("stock", 0),
                    categoria = objeto.optString("categoria")
                )
            )
        }
        return productos
    }
}
