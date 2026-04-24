package com.example.rentavan.data.model.profile

data class NuevaCaravana(
    val marca: String,
    val modelo: String,
    val precioPorDia: String, // Usamos String para el campo de texto y lo convertiremos luego
    val plazas: String,
    val descripcion: String
)

data class AnadirResponse(
    val exito: Boolean,
    val mensaje: String
)