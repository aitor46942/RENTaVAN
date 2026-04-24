package com.example.rentavan.data.model.auth

data class RegisterRequest(
    val usuario: String,
    val correo: String,
    val nombre: String,
    val apellidos: String,
    val contrasena: String
)

data class RegisterResponse(
    val exito: Boolean,
    val mensaje: String
)