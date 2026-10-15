package com.udb.tienda.fh190090.er241617.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.udb.tienda.fh190090.er241617.controller.ReporteController
import com.udb.tienda.fh190090.er241617.databinding.ActivityReporteBinding

class ReporteActivity : BaseActivity() {

    private lateinit var binding: ActivityReporteBinding
    private lateinit var adapter: ProductoAdapter
    private val controller = ReporteController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReporteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductoAdapter(emptyList()) { }
        binding.recyclerStockBajo.layoutManager = LinearLayoutManager(this)
        binding.recyclerStockBajo.adapter = adapter

        binding.progressReporte.visibility = View.VISIBLE
        controller.generarReporte(
            onExito = { resumen ->
                binding.progressReporte.visibility = View.GONE
                binding.tvTotal.text = "Total de productos: ${resumen.totalProductos}"
                binding.tvValor.text =
                    "Valor total del inventario: ${resumen.valorInventario.comoDinero()}"
                adapter.actualizar(resumen.stockBajo)
                binding.tvSinStockBajo.visibility =
                    if (resumen.stockBajo.isEmpty()) View.VISIBLE else View.GONE
            },
            onError = { mensaje ->
                binding.progressReporte.visibility = View.GONE
                mostrarMensaje(mensaje)
            }
        )
    }

    override fun onDestroy() {
        controller.cancelar()
        super.onDestroy()
    }
}
