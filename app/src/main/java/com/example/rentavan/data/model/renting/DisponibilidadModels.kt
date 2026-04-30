package com.example.rentavan.data.model.renting

data class DisponibilidadRequest(
    val caravanaId: String,
    val fechaInicio: String,
    val fechaFin: String
)

data class DisponibilidadResponse(
    val disponible: Boolean,
    val mensaje: String
)