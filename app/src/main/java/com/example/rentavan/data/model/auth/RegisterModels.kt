package com.example.rentavan.data.model.auth

// Ubicación: com.example.rentavan.data.model.auth.RegisterModels.kt
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val telefono: String,
    val contrasena: String
)

data class RegisterResponse(
    val exito: Boolean,
    val mensaje: String
)
