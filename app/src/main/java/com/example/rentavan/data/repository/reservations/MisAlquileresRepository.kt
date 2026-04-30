package com.example.rentavan.data.repository.reservations

import com.example.rentavan.data.model.reservations.Alquiler
import com.example.rentavan.data.model.reservations.CancelarAlquilerResponse
import kotlinx.coroutines.delay

suspend fun obtenerMisAlquileres(): Result<List<Alquiler>> {
    delay(800)
    val lista = listOf(
        Alquiler(
            reservaId = 0,
            modelo = "Volkswagen Grand California",
            anio = 2022,
            peso = "3500 kg",
            matricula = "1234-ABC",
            precio = 63.0 ,
            fechaInicio = "01/06/2026",
            fechaFin = "10/06/2026"
        )
    )
    return Result.success(lista)
}

suspend fun cancelarAlquiler(reservaId: String): Result<CancelarAlquilerResponse> {
    delay(1000)
    return Result.success(CancelarAlquilerResponse(exito = true, mensaje = "Alquiler cancelado correctamente"))
}
