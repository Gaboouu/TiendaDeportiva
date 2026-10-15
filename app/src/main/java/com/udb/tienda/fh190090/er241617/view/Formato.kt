package com.udb.tienda.fh190090.er241617.view

import android.content.Context
import android.widget.Toast
import java.util.Locale

fun Double.comoDinero(): String = String.format(Locale.US, "\$%.2f", this)

fun Context.mostrarMensaje(texto: String) {
    Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
}
