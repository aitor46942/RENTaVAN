package com.example.rentavan.data.model.reservations

data class ModificarReservaRequest(
    val reservaId: Int,
    val nViajeros: Int,
    val fechaInicio: String,
    val fechaFin: String
)

data class ModificarReservaResponse(
    val exito: Boolean,
    val mensaje: String
)