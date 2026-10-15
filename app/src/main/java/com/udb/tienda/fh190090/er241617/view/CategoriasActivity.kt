package com.udb.tienda.fh190090.er241617.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.udb.tienda.fh190090.er241617.controller.CategoriaController
import com.udb.tienda.fh190090.er241617.databinding.ActivityCategoriasBinding
import com.udb.tienda.fh190090.er241617.databinding.DialogCategoriaBinding
import com.udb.tienda.fh190090.er241617.model.Categoria

class CategoriasActivity : BaseActivity() {

    private lateinit var binding: ActivityCategoriasBinding
    private lateinit var adapter: CategoriaAdapter
    private val controller = CategoriaController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CategoriaAdapter(emptyList()) { categoria -> mostrarOpciones(categoria) }
        binding.recyclerCategorias.layoutManager = LinearLayoutManager(this)
        binding.recyclerCategorias.adapter = adapter

        binding.fabAgregarCategoria.setOnClickListener { mostrarFormulario(null) }
        cargarCategorias()
    }

    private fun cargarCategorias() {
        controller.cargarCategorias(
            onExito = { categorias -> adapter.actualizar(categorias) },
            onError = { mensaje -> mostrarMensaje(mensaje) }
        )
    }

    private fun mostrarOpciones(categoria: Categoria) {
        AlertDialog.Builder(this)
            .setTitle(categoria.nombre)
            .setItems(arrayOf("Modificar", "Eliminar")) { _, opcion ->
                if (opcion == 0) mostrarFormulario(categoria) else confirmarEliminar(categoria)
            }
            .show()
    }

    private fun mostrarFormulario(existente: Categoria?) {
        val dialogBinding = DialogCategoriaBinding.inflate(layoutInflater)
        if (existente != null) {
            dialogBinding.etNombreCategoria.setText(existente.nombre)
            dialogBinding.etDescripcionCategoria.setText(existente.descripcion)
        }

        val dialogo = AlertDialog.Builder(this)
            .setTitle(if (existente == null) "Nueva categoría" else "Modificar categoría")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar", null) // se define abajo para no cerrar si hay error
            .setNegativeButton("Cancelar", null)
            .create()

        dialogo.setOnShowListener {
            dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val nombre = dialogBinding.etNombreCategoria.text.toString().trim()
                val descripcion = dialogBinding.etDescripcionCategoria.text.toString().trim()

                val error = controller.validar(nombre, descripcion)
                if (error != null) {
                    mostrarMensaje(error)
                    return@setOnClickListener
                }

                val categoria = Categoria(nombre = nombre, descripcion = descripcion)
                val alExito = {
                    mostrarMensaje("Categoría guardada")
                    dialogo.dismiss()
                    cargarCategorias()
                }
                if (existente == null) {
                    controller.crear(categoria, alExito) { mensaje -> mostrarMensaje(mensaje) }
                } else {
                    val id = existente.id ?: return@setOnClickListener
                    controller.actualizar(id, categoria, alExito) { mensaje -> mostrarMensaje(mensaje) }
                }
            }
        }
        dialogo.show()
    }

    private fun confirmarEliminar(categoria: Categoria) {
        val id = categoria.id ?: return
        AlertDialog.Builder(this)
            .setTitle("Eliminar categoría")
            .setMessage("¿Seguro que desea eliminar \"${categoria.nombre}\"?")
            .setPositiveButton("Eliminar") { _, _ ->
                controller.eliminar(
                    id,
                    onExito = {
                        mostrarMensaje("Categoría eliminada")
                        cargarCategorias()
                    },
                    onError = { mensaje -> mostrarMensaje(mensaje) }
                )
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
