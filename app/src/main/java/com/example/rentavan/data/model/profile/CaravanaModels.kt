package com.example.rentavan.data.model.profile

data class Caravana(
    val id: String,
    val marca: String,
    val modelo: String,
    val precioPorDia: Double,
    val plazas: Int
)

data class CaravanaResponse(
    val idCaravana: Long,
    val modelo: String,
    val descripcion: String,
    val propietario: PropietarioResumen?,
    val marca: String? = null,
    val precioPorDia: Double = 90.0,
    val plazas: Int = 4,
    val latitud: Double? = null,
    val longitud: Double? = null
) {
    val id: String get() = idCaravana.toString()
    val nombre: String get() = modelo
}

data class PropietarioResumen(
    val idUsuario: Long,
    val nombre: String,
    val telefono: String
)

// NUEVO
data class NuevaCaravana(
    val modelo: String,
    val descripcion: String,
    val fechaInicio: String,
    val fechaFin: String
)

data class AnadirResponse(
    val exito: Boolean,
    val mensaje: String
)
