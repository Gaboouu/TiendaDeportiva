package com.udb.tienda.fh190090.er241617.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.udb.tienda.fh190090.er241617.R
import com.udb.tienda.fh190090.er241617.controller.ProductoController
import com.udb.tienda.fh190090.er241617.databinding.ActivityMainBinding
import com.udb.tienda.fh190090.er241617.model.Producto

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductoAdapter
    private val controller = ProductoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvNombreTienda.text = getString(R.string.nombre_tienda)

        adapter = ProductoAdapter(emptyList()) { producto -> mostrarOpciones(producto) }
        binding.recyclerProductos.layoutManager = LinearLayoutManager(this)
        binding.recyclerProductos.adapter = adapter

        binding.fabAgregar.setOnClickListener {
            startActivity(Intent(this, CrearProductoActivity::class.java))
        }
        binding.btnCatalogo.setOnClickListener {
            startActivity(Intent(this, CatalogoActivity::class.java))
        }
        binding.btnCategorias.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
        }
        binding.btnReporte.setOnClickListener {
            startActivity(Intent(this, ReporteActivity::class.java))
        }
        binding.btnBuscarId.setOnClickListener { buscarPorId() }
    }

    override fun onResume() {
        super.onResume()
        cargarProductos()
    }

    private fun cargarProductos() {
        controller.cargarProductos(
            onExito = { productos -> adapter.actualizar(productos) },
            onError = { mensaje -> mostrarMensaje(mensaje) }
        )
    }

    private fun mostrarOpciones(producto: Producto) {
        AlertDialog.Builder(this)
            .setTitle(producto.nombre)
            .setItems(arrayOf("Modificar", "Eliminar")) { _, opcion ->
                if (opcion == 0) abrirEdicion(producto) else confirmarEliminar(producto)
            }
            .show()
    }

    private fun abrirEdicion(producto: Producto) {
        val intent = Intent(this, ActualizarProductoActivity::class.java).apply {
            putExtra(Extras.ID, producto.id)
            putExtra(Extras.NOMBRE, producto.nombre)
            putExtra(Extras.DESCRIPCION, producto.descripcion)
            putExtra(Extras.PRECIO, producto.precio)
            putExtra(Extras.STOCK, producto.stock)
            putExtra(Extras.CATEGORIA, producto.categoria)
        }
        startActivity(intent)
    }

    private fun confirmarEliminar(producto: Producto) {
        val id = producto.id ?: return
        AlertDialog.Builder(this)
            .setTitle("Eliminar producto")
            .setMessage("¿Seguro que desea eliminar \"${producto.nombre}\"?")
            .setPositiveButton("Eliminar") { _, _ ->
                controller.eliminar(
                    id,
                    onExito = {
                        mostrarMensaje("Producto eliminado")
                        cargarProductos()
                    },
                    onError = { mensaje -> mostrarMensaje(mensaje) }
                )
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun buscarPorId() {
        controller.buscarPorId(
            binding.etBuscarId.text.toString(),
            onExito = { producto -> mostrarDetalle(producto) },
            onNoEncontrado = {
                AlertDialog.Builder(this)
                    .setTitle("Búsqueda por ID")
                    .setMessage("Producto no encontrado")
                    .setPositiveButton("Aceptar", null)
                    .show()
            },
            onError = { mensaje -> mostrarMensaje(mensaje) }
        )
    }

    private fun mostrarDetalle(producto: Producto) {
        val detalle = "ID: ${producto.id}\n" +
            "Descripción: ${producto.descripcion}\n" +
            "Precio: ${producto.precio.comoDinero()}\n" +
            "Stock: ${producto.stock}\n" +
            "Categoría: ${producto.categoria}"
        AlertDialog.Builder(this)
            .setTitle(producto.nombre)
            .setMessage(detalle)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}
