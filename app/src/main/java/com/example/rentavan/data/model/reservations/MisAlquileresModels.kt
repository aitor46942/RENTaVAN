package com.example.rentavan.data.model.reservations

data class Alquiler(
    val reservaId: Int,
    val modelo: String,
    val anio: Int,
    val peso: String,
    val matricula: String,
    val precio: Double,
    val fechaInicio: String,
    val fechaFin: String
)

data class CancelarAlquilerRequest(
    val reservaId: Int
)

data class CancelarAlquilerResponse(
    val exito: Boolean,
    val mensaje: String
)