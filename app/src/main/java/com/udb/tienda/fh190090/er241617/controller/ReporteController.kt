package com.udb.tienda.fh190090.er241617.controller

import android.util.Log
import com.udb.tienda.fh190090.er241617.model.ReporteRepository
import com.udb.tienda.fh190090.er241617.model.ResumenInventario
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ReporteController(private val repositorio: ReporteRepository = ReporteRepository()) {

    companion object {
        const val UMBRAL_STOCK_BAJO = 5
        private const val TAG = "ReporteController"
    }

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    fun generarReporte(onExito: (ResumenInventario) -> Unit, onError: (String) -> Unit) {
        scope.launch {
            try {
                val productos = withContext(Dispatchers.IO) { repositorio.obtenerProductos() }

                val total = productos.size
                val valor = productos.sumOf { it.precio * it.stock }
                val bajos = productos.filter { it.stock < UMBRAL_STOCK_BAJO }

                onExito(ResumenInventario(total, valor, bajos))
            } catch (e: CancellationException) {
                throw e // la pantalla se cerró: no es un error
            } catch (e: IOException) {
                Log.e(TAG, "Error de red o respuesta no exitosa", e)
                onError("No se pudo obtener el reporte. Revise su conexión y la URL de la API.")
            } catch (e: Exception) {
                Log.e(TAG, "Error al convertir el JSON", e)
                onError("Error al procesar los datos recibidos.")
            }
        }
    }

    fun cancelar() {
        scope.cancel()
    }
}
