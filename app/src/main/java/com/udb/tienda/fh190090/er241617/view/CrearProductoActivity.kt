package com.udb.tienda.fh190090.er241617.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.udb.tienda.fh190090.er241617.R
import com.udb.tienda.fh190090.er241617.controller.CategoriaController
import com.udb.tienda.fh190090.er241617.controller.ProductoController
import com.udb.tienda.fh190090.er241617.controller.ResultadoValidacion
import com.udb.tienda.fh190090.er241617.databinding.ActivityFormProductoBinding

class CrearProductoActivity : BaseActivity() {

    private lateinit var binding: ActivityFormProductoBinding
    private val productoController = ProductoController()
    private val categoriaController = CategoriaController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTituloForm.setText(R.string.titulo_crear_producto)
        binding.etNombre.setText(intent.getStringExtra(Extras.NOMBRE) ?: "")
        binding.etDescripcion.setText(intent.getStringExtra(Extras.DESCRIPCION) ?: "")
        if (intent.hasExtra(Extras.PRECIO)) {
            binding.etPrecio.setText(intent.getDoubleExtra(Extras.PRECIO, 0.0).toString())
        }

        cargarCategorias()
        binding.btnGuardar.setOnClickListener { guardar() }
    }

    private fun cargarCategorias() {
        categoriaController.cargarCategorias(
            onExito = { categorias ->
                val nombres = categorias.map { it.nombre }
                binding.spCategoria.adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres)
                if (nombres.isEmpty()) mostrarMensaje("Primero cree al menos una categoría")
            },
            onError = { mensaje -> mostrarMensaje(mensaje) }
        )
    }

    private fun guardar() {
        val resultado = productoController.validar(
            binding.etNombre.text.toString(),
            binding.etDescripcion.text.toString(),
            binding.etPrecio.text.toString(),
            binding.etStock.text.toString(),
            binding.spCategoria.selectedItem as? String
        )
        when (resultado) {
            is ResultadoValidacion.Invalido -> mostrarMensaje(resultado.mensaje)
            is ResultadoValidacion.Valido -> {
                binding.btnGuardar.isEnabled = false
                productoController.crear(
                    resultado.producto,
                    onExito = {
                        mostrarMensaje("Producto guardado en el inventario")
                        finish()
                    },
                    onError = { mensaje ->
                        binding.btnGuardar.isEnabled = true
                        mostrarMensaje(mensaje)
                    }
                )
            }
        }
    }
}
