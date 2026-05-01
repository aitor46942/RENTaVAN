package com.example.rentavan.data.repository.renting

import com.example.rentavan.data.model.renting.DisponibilidadRequest
import com.example.rentavan.data.model.renting.DisponibilidadResponse
import kotlinx.coroutines.delay

suspend fun consultarDisponibilidad(request: DisponibilidadRequest): Result<DisponibilidadResponse> {
    delay(1000)
    return Result.success(
        DisponibilidadResponse(
            disponible = true,
            mensaje = "Caravana disponible en ese periodo"
        )
    )
}
