package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.profile.UsuarioPerfil
import kotlinx.coroutines.delay

class ProfileRepository {
    // Simulamos la descarga de datos del perfil desde el servidor
    suspend fun obtenerPerfilUsuario(): Result<UsuarioPerfil> {
        delay(1200) // Simulamos el tiempo de carga de internet

        // Datos simulados (en el futuro vendrán de la base de datos)
        val perfilSimulado = UsuarioPerfil(
            nombre = "Aitor",
            apellidos = "García",
            correo = "admin@rentavan.com",
            telefono = "+34 600 123 456",
            fechaRegistro = "Octubre 2023"
        )

        return Result.success(perfilSimulado)
    }
}