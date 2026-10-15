package com.udb.tienda.fh190090.er241617.controller

import com.udb.tienda.fh190090.er241617.model.CatalogoRepository
import com.udb.tienda.fh190090.er241617.model.ProductoProveedor

class CatalogoController(private val repositorio: CatalogoRepository = CatalogoRepository()) {

    fun cargarCatalogo(onExito: (List<ProductoProveedor>) -> Unit, onError: (String) -> Unit) {
        repositorio.listar(onExito) { _, mensaje -> onError(mensaje) }
    }

    fun buscar(texto: String, onExito: (List<ProductoProveedor>) -> Unit, onError: (String) -> Unit) {
        if (texto.isBlank()) {
            cargarCatalogo(onExito, onError)
        } else {
            repositorio.buscar(texto.trim(), onExito) { _, mensaje -> onError(mensaje) }
        }
    }
}
