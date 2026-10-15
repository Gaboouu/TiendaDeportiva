package com.udb.tienda.fh190090.er241617.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.udb.tienda.fh190090.er241617.controller.CatalogoController
import com.udb.tienda.fh190090.er241617.databinding.ActivityCatalogoBinding
import com.udb.tienda.fh190090.er241617.model.ProductoProveedor

class CatalogoActivity : BaseActivity() {

    private lateinit var binding: ActivityCatalogoBinding
    private lateinit var adapter: CatalogoAdapter
    private val controller = CatalogoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CatalogoAdapter(emptyList()) { producto -> importar(producto) }
        binding.recyclerCatalogo.layoutManager = LinearLayoutManager(this)
        binding.recyclerCatalogo.adapter = adapter

        binding.searchCatalogo.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                cargar(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) cargar("")
                return true
            }
        })

        cargar("")
    }

    private fun cargar(texto: String) {
        binding.progressCatalogo.visibility = View.VISIBLE
        controller.buscar(
            texto,
            onExito = { productos ->
                binding.progressCatalogo.visibility = View.GONE
                adapter.actualizar(productos)
                binding.tvVacio.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE
                if (productos.isEmpty() && texto.isNotBlank()) {
                    mostrarMensaje("No se encontraron productos para \"$texto\"")
                }
            },
            onError = { mensaje ->
                binding.progressCatalogo.visibility = View.GONE
                mostrarMensaje(mensaje)
            }
        )
    }

    private fun importar(producto: ProductoProveedor) {
        val intent = Intent(this, CrearProductoActivity::class.java).apply {
            putExtra(Extras.NOMBRE, producto.nombre)
            putExtra(Extras.DESCRIPCION, producto.descripcion)
            putExtra(Extras.PRECIO, producto.precio)
        }
        startActivity(intent)
    }
}
