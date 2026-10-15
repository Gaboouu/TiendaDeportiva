package com.udb.tienda.fh190090.er241617.model

class CategoriaRepository(private val api: InventarioApi = RetrofitClient.inventarioApi) {

    fun listar(onExito: (List<Categoria>) -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.listarCategorias(), { onExito(it ?: emptyList()) }, onError)
    }

    fun crear(categoria: Categoria, onExito: () -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.crearCategoria(categoria), { onExito() }, onError)
    }

    fun actualizar(id: String, categoria: Categoria, onExito: () -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.actualizarCategoria(id, categoria), { onExito() }, onError)
    }

    fun eliminar(id: String, onExito: () -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.eliminarCategoria(id), { onExito() }, onError)
    }
}
