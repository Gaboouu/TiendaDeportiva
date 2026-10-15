package com.udb.tienda.fh190090.er241617.controller

import com.udb.tienda.fh190090.er241617.model.Categoria
import com.udb.tienda.fh190090.er241617.model.CategoriaRepository

class CategoriaController(private val repositorio: CategoriaRepository = CategoriaRepository()) {

    fun cargarCategorias(onExito: (List<Categoria>) -> Unit, onError: (String) -> Unit) {
        repositorio.listar(onExito) { _, mensaje -> onError(mensaje) }
    }

    fun crear(categoria: Categoria, onExito: () -> Unit, onError: (String) -> Unit) {
        repositorio.crear(categoria, onExito) { _, mensaje -> onError(mensaje) }
    }

    fun actualizar(id: String, categoria: Categoria, onExito: () -> Unit, onError: (String) -> Unit) {
        repositorio.actualizar(id, categoria, onExito) { _, mensaje -> onError(mensaje) }
    }

    fun eliminar(id: String, onExito: () -> Unit, onError: (String) -> Unit) {
        repositorio.eliminar(id, onExito) { _, mensaje -> onError(mensaje) }
    }

    fun validar(nombre: String, descripcion: String): String? = when {
        nombre.isBlank() -> "El nombre de la categoría es obligatorio"
        descripcion.isBlank() -> "La descripción es obligatoria"
        else -> null
    }
}
