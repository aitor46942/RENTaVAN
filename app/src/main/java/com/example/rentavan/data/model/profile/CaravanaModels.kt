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
    val location: GeometryModel,
    val propietario: PropietarioResumen
)

data class GeometryModel(
    val type: String, // "Point"
    val coordinates: List<Double> // [lng, lat]
)

data class PropietarioResumen(
    val idUsuario: Long,
    val nombre: String,
    val telefono: String
)