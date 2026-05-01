package com.example.rentavan.data.repository.settings

import com.example.rentavan.data.model.settings.LogoutResponse
import kotlinx.coroutines.delay

suspend fun cerrarSesionUsuario(): Result<LogoutResponse> {
    delay(500) // Simulamos el tiempo de borrado

    return Result.success(
        LogoutResponse(exito = true, mensaje = "Sesión cerrada correctamente")
    )
}
