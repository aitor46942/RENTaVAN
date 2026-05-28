package com.example.rentavan.data.sessions

object UserSession {
    var idUsuario: Long? = null
    var nombre: String? = null
    var email: String? = null

    fun cerrarSesion() {
        idUsuario = null
        nombre = null
        email = null
    }
}