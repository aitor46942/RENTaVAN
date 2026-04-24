package com.example.rentavan.data.model.auth

data class LoginRequest(
    val usuario: String,
    val contrasena: String
)

data class LoginResponse(
    val token: String,
    val mensaje: String,
    val exito: Boolean
)