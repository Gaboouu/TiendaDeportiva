package com.udb.tienda.fh190090.er241617.controller

import com.udb.tienda.fh190090.er241617.model.Producto

sealed class ResultadoValidacion {
    data class Valido(val producto: Producto) : ResultadoValidacion()
    data class Invalido(val mensaje: String) : ResultadoValidacion()
}
