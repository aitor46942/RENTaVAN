package com.example.rentavan.data.repository.renting

import com.example.rentavan.data.model.renting.AlquilerRequest
import com.example.rentavan.data.model.renting.AlquilerResponse
import com.example.rentavan.data.model.renting.CaravanaDetalle
import kotlinx.coroutines.delay

suspend fun obtenerDetalleCaravana(caravanaId: String): Result<CaravanaDetalle> {
    delay(800)
    //caravana simulada
    val detalle = CaravanaDetalle(
        id = caravanaId,
        modelo = "Volkswagen Grand California",
        anio = "2022",
        peso = "3500 kg",
        matricula = "1234-ABC",
        informacionAdicional = "Incluye kit de cocina y climatizador"
    )
    return Result.success(detalle)
}

suspend fun realizarAlquiler(request: AlquilerRequest): Result<AlquilerResponse> {
    delay(1500)
    return Result.success(
        AlquilerResponse(
            exito = true,
            mensaje = "Alquiler realizado correctamente"
        )
    )
}
