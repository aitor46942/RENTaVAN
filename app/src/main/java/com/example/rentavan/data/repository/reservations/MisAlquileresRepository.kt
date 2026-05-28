package com.example.rentavan.data.repository.reservations

import com.example.rentavan.data.model.reservations.Alquiler
import com.example.rentavan.data.model.reservations.CancelarAlquilerResponse
import com.example.rentavan.data.model.network.AlquilerBackendResponse
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.sessions.UserSession

suspend fun obtenerMisAlquileresBackend(): Result<List<AlquilerBackendResponse>> {
    val idCliente = UserSession.idUsuario
        ?: return Result.failure(Exception("Sesión no iniciada"))

    return try {
        val response = RetrofitClient.apiService.listarAlquileresCliente(idCliente)
        if (response.isSuccessful && response.body() != null)
            Result.success(response.body()!!)
        else
            Result.failure(Exception("Error ${response.code()}"))
    } catch (e: Exception) { Result.failure(e) }
}

suspend fun cancelarAlquilerBackend(idAlquiler: Long): Result<Unit> {
    return try {
        val response = RetrofitClient.apiService.cancelarAlquiler(idAlquiler)
        if (response.isSuccessful) Result.success(Unit)
        else Result.failure(Exception("Error ${response.code()}"))
    } catch (e: Exception) { Result.failure(e) }
}





suspend fun obtenerMisAlquileres(): Result<List<Alquiler>> {
    return obtenerMisAlquileresBackend().map { listaBackend ->
        listaBackend
            .filter { it.estado != "CANCELADO" }
            .map { backend ->
                Alquiler(
                    reservaId = backend.idAlquiler.toInt(),
                    modelo = backend.modeloCaravana,
                    anio = 2024,
                    peso = "3500 kg",
                    matricula = "0000-XXX",
                    precio = 100.0,
                    fechaInicio = backend.fechaInicio,
                    fechaFin = backend.fechaFin
                )
            }
    }
}

suspend fun cancelarAlquiler(reservaId: Int): Result<CancelarAlquilerResponse> {
    return cancelarAlquilerBackend(reservaId.toLong()).map {
        CancelarAlquilerResponse(exito = true, mensaje = "Alquiler cancelado correctamente")
    }
}
