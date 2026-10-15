package com.udb.tienda.fh190090.er241617.model

class ProductoRepository(private val api: InventarioApi = RetrofitClient.inventarioApi) {

    fun listar(onExito: (List<Producto>) -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.listarProductos(), { onExito(it ?: emptyList()) }, onError)
    }

    fun obtenerPorId(id: String, onExito: (Producto) -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.obtenerProducto(id), { cuerpo ->
            if (cuerpo != null) onExito(cuerpo) else onError(404, "Producto no encontrado")
        }, onError)
    }

    fun crear(producto: Producto, onExito: () -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.crearProducto(producto), { onExito() }, onError)
    }

    fun actualizar(id: String, producto: Producto, onExito: () -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.actualizarProducto(id, producto), { onExito() }, onError)
    }

    fun eliminar(id: String, onExito: () -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.eliminarProducto(id), { onExito() }, onError)
    }
}
