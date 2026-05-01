package com.example.rentavan.data.model.renting


    data class CaravanaDetalle(
        val id: String,
        val modelo: String,
        val anio: String,
        val peso: String,
        val matricula: String,
        val informacionAdicional: String
    )

    data class AlquilerRequest(
        val caravanaId: String,
        val dni: String,
        val nTarjeta: String,
        val nViajeros: String,
        val fechaInicio: String,
        val fechaFin: String
    )

    data class AlquilerResponse(
        val exito: Boolean,
        val mensaje: String
    )
