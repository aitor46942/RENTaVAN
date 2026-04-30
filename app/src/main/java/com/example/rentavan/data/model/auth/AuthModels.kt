package com.example.rentavan.data.model.auth

data class LoginRequest(
    val usuario: String,
    val contrasena: String
)

data class AuthResponse(
    val exito: Boolean,
    val mensaje: String,
    val idUsuario: Long?,
    val nombre: String?
)
