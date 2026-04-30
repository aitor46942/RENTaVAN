package com.example.rentavan.data.repository.reservations

import com.example.rentavan.data.model.reservations.Alquiler
import com.example.rentavan.data.model.reservations.ModificarReservaRequest
import com.example.rentavan.data.model.reservations.ModificarReservaResponse
import kotlinx.coroutines.delay

suspend fun obtenerDetalleReserva(reservaId: Int): Result<Alquiler> {
    delay(800)
    val detalle = Alquiler(
        reservaId = reservaId,
        modelo = "Volkswagen Grand California",
        anio = 2022,
        peso = "3500 kg",
        matricula = "1234-ABC",
        precio = 63.0,
        fechaInicio = "01/06/2026",
        fechaFin = "10/06/2026"
    )
    return Result.success(detalle)
}

suspend fun modificarReserva(request: ModificarReservaRequest): Result<ModificarReservaResponse> {
    delay(1500)
    return Result.success(
        ModificarReservaResponse(
            exito = true,
            mensaje = "Reserva modificada correctamente"
        )
    )
}
