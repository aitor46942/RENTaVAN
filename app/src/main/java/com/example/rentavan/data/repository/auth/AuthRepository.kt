package com.example.rentavan.data.repository.auth

import com.example.rentavan.data.model.auth.LoginRequest
import com.example.rentavan.data.model.auth.LoginResponse
import com.example.rentavan.data.model.auth.RegisterRequest
import com.example.rentavan.data.model.auth.RegisterResponse
import kotlinx.coroutines.delay

class AuthRepository {
    // Función suspendida porque hará una llamada de red asíncrona
    suspend fun login(request: LoginRequest): Result<LoginResponse> {

        delay(1000)

        // Simulamos la respuesta del Backend
        return if (request.usuario == "admin" && request.contrasena == "1234") {
            Result.success(LoginResponse("token_falso_123", "Login exitoso", true))
        } else {
            Result.failure(Exception("Credenciales incorrectas"))
        }
    }

    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        // Simulamos el tiempo de carga de internet (1.5 segundos)
        kotlinx.coroutines.delay(1500)

        // Simulamos que siempre va bien de momento
        return Result.success(RegisterResponse(true, "Usuario registrado con éxito"))
    }
}