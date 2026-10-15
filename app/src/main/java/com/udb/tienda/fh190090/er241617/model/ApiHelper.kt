package com.udb.tienda.fh190090.er241617.model

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias OnError = (Int, String) -> Unit

object ApiHelper {

    private const val TAG = "ApiHelper"

    fun <T> ejecutar(call: Call<T>, onExito: (T?) -> Unit, onError: OnError) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onExito(response.body())
                } else {
                    Log.e(TAG, "Respuesta no exitosa. Código HTTP: ${response.code()}")
                    onError(response.code(), mensajePorCodigo(response.code()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e(TAG, "Fallo de red", t)
                onError(0, "No se pudo conectar. Revise su conexión a Internet.")
            }
        })
    }

    private fun mensajePorCodigo(codigo: Int): String = when (codigo) {
        404 -> "No se encontró el recurso (404)"
        in 500..599 -> "Error en el servidor ($codigo). Intente más tarde."
        else -> "La petición no fue exitosa (código $codigo)"
    }
}
