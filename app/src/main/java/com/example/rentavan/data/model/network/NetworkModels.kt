package com.example.rentavan.data.model.network

// Respuesta de datos de usuario (GET /api/usuarios/{id})
data class UsuarioBackendResponse(
    val idUsuario: Long,
    val nombre: String,
    val email: String,
    val telefono: String
)

// Para actualizar un usuario (PUT /api/usuarios/{id})
data class ActualizarUsuarioRequest(
    val nombre: String,
    val telefono: String
)

// Para crear una caravana (POST /api/caravanas)
data class CaravanaBackendDTO(
    val modelo: String,
    val descripcion: String,
    val idPropietario: Long,
    val latitud: Double? = null,
    val longitud: Double? = null
)

// Para periodos de disponibilidad (GET y POST /api/periodos)
data class PeriodoDisponibilidadResponse(
    val idPeriodo: Long? = null,
    val idCaravana: Long,
    val fechaInicio: String,   // formato ISO: "2026-06-01"
    val fechaFin: String
)

// Para crear un alquiler (POST /api/alquileres)
data class AlquilerBackendRequest(
    val idCaravana: Long,
    val idCliente: Long,
    val idPeriodo: Long,
    val fechaInicio: String,
    val fechaFin: String
)

// Respuesta de alquiler del backend
data class AlquilerBackendResponse(
    val idAlquiler: Long,
    val idCaravana: Long,
    val modeloCaravana: String,
    val idCliente: Long,
    val fechaInicio: String,
    val fechaFin: String,
    val estado: String
)