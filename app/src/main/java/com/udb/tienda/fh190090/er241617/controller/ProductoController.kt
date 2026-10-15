package com.udb.tienda.fh190090.er241617.controller

import com.udb.tienda.fh190090.er241617.model.Producto
import com.udb.tienda.fh190090.er241617.model.ProductoRepository

class ProductoController(private val repositorio: ProductoRepository = ProductoRepository()) {

    fun cargarProductos(onExito: (List<Producto>) -> Unit, onError: (String) -> Unit) {
        repositorio.listar(onExito) { _, mensaje -> onError(mensaje) }
    }

    fun buscarPorId(
        idTexto: String,
        onExito: (Producto) -> Unit,
        onNoEncontrado: () -> Unit,
        onError: (String) -> Unit
    ) {
        val id = idTexto.trim()
        if (id.isEmpty()) {
            onError("Ingrese el ID del producto a buscar")
            return
        }
        repositorio.obtenerPorId(id, onExito) { codigo, mensaje ->
            if (codigo == 404) onNoEncontrado() else onError(mensaje)
        }
    }

    fun crear(producto: Producto, onExito: () -> Unit, onError: (String) -> Unit) {
        repositorio.crear(producto, onExito) { _, mensaje -> onError(mensaje) }
    }

    fun actualizar(id: String, producto: Producto, onExito: () -> Unit, onError: (String) -> Unit) {
        repositorio.actualizar(id, producto, onExito) { _, mensaje -> onError(mensaje) }
    }

    fun eliminar(id: String, onExito: () -> Unit, onError: (String) -> Unit) {
        repositorio.eliminar(id, onExito) { _, mensaje -> onError(mensaje) }
    }

    fun validar(
        nombre: String,
        descripcion: String,
        precioTexto: String,
        stockTexto: String,
        categoria: String?
    ): ResultadoValidacion {
        if (nombre.isBlank() || descripcion.isBlank() || precioTexto.isBlank() || stockTexto.isBlank()) {
            return ResultadoValidacion.Invalido("Complete todos los campos")
        }
        val precio = precioTexto.trim().toDoubleOrNull()
            ?: return ResultadoValidacion.Invalido("Ingrese un precio válido")
        if (precio <= 0) {
            return ResultadoValidacion.Invalido("El precio debe ser mayor que 0")
        }
        val stock = stockTexto.trim().toIntOrNull()
            ?: return ResultadoValidacion.Invalido("El stock debe ser un número entero")
        if (stock < 0) {
            return ResultadoValidacion.Invalido("El stock no puede ser negativo")
        }
        if (categoria.isNullOrBlank()) {
            return ResultadoValidacion.Invalido("Seleccione una categoría")
        }
        return ResultadoValidacion.Valido(
            Producto(
                nombre = nombre.trim(),
                descripcion = descripcion.trim(),
                precio = precio,
                stock = stock,
                categoria = categoria
            )
        )
    }
}
