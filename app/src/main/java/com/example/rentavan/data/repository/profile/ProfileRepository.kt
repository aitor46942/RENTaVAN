package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.profile.UsuarioPerfil
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.sessions.UserSession

class ProfileRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun obtenerPerfilUsuario(): Result<UsuarioPerfil> {
        val idUsuario = UserSession.idUsuario
            ?: return Result.failure(Exception("No hay sesión activa"))

        return try {
            val response = apiService.obtenerUsuario(idUsuario)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(
                    UsuarioPerfil(
                        nombre   = body.nombre,
                        email    = body.email,
                        telefono = body.telefono
                    )
                )
            } else {
                Result.success(perfilDesdeSesion())
            }
        } catch (e: Exception) {
            Result.success(perfilDesdeSesion())
        }
    }

    private fun perfilDesdeSesion() = UsuarioPerfil(
        nombre   = UserSession.nombre   ?: "Usuario",
        email    = UserSession.email    ?: "",
        telefono = ""
    )
}