package com.example.rentavan.data.model.profile

data class ActualizarCaravanaRequest(
    val idCaravana: String,
    val modelo: String,
    val anio: String,
    val peso: String,
    val matricula: String,
    val nombreTitular: String,
    val telefono: String,
    val email: String,
    val fechaInicio: String,
    val fechaFin: String,
    val darDeAlta: Boolean,
    val numPlazas: String,
    val precio: String
)

data class ActualizarResponse(
    val exito: Boolean,
    val mensaje: String
)