package com.example.rentavan.data.repository.auth

import com.example.rentavan.data.model.auth.LoginRequest
import com.example.rentavan.data.model.auth.AuthResponse
import com.example.rentavan.data.model.auth.RegisterRequest
import com.example.rentavan.data.model.auth.RegisterResponse
import com.example.rentavan.data.network.RentavanApiService
import kotlinx.coroutines.delay

class AuthRepository(private val apiService: RentavanApiService) {

    suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return try {
            val response = apiService.login(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de autenticación"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registrar(request: RegisterRequest): Result<AuthResponse> {
        return try {
            val response = apiService.register(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                // Extraemos el error real del cuerpo de la respuesta o el código HTTP
                val errorMsg = response.errorBody()?.string() ?: "Error servidor: ${response.code()}"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
