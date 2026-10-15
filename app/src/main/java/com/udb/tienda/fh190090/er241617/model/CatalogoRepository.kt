package com.udb.tienda.fh190090.er241617.model

class CatalogoRepository(private val api: CatalogoApi = RetrofitClient.catalogoApi) {

    fun listar(onExito: (List<ProductoProveedor>) -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.obtenerProductos(30), { onExito(it?.productos ?: emptyList()) }, onError)
    }

    fun buscar(texto: String, onExito: (List<ProductoProveedor>) -> Unit, onError: OnError) {
        ApiHelper.ejecutar(api.buscarProductos(texto), { onExito(it?.productos ?: emptyList()) }, onError)
    }
}
