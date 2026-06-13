package com.example.rentavan.data.repository.reservations

import com.example.rentavan.data.model.reservations.Alquiler
import com.example.rentavan.data.model.reservations.CancelarAlquilerResponse
import com.example.rentavan.data.model.network.AlquilerBackendResponse
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.sessions.UserSession
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

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
        val emptyBody = "".toRequestBody("application/json".toMediaType())
        val response = RetrofitClient.apiService.cancelarAlquiler(idAlquiler, emptyBody)
        if (response.isSuccessful) Result.success(Unit)
        else {
            val errorBody = response.errorBody()?.string() ?: ""
            Result.failure(Exception("HTTP ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) { Result.failure(e) }
}





suspend fun obtenerMisAlquileres(): Result<List<Alquiler>> {
    val alquileresResult = obtenerMisAlquileresBackend()
    if (alquileresResult.isFailure) return Result.failure(alquileresResult.exceptionOrNull()!!)

    val caravanasMap = try {
        val response = RetrofitClient.apiService.listarCaravanas()
        if (response.isSuccessful) response.body()?.associateBy { it.idCaravana } ?: emptyMap()
        else emptyMap()
    } catch (e: Exception) { emptyMap() }

    return alquileresResult.map { listaBackend ->
        listaBackend
            .filter { it.estado != "CANCELADO" }
            .map { backend ->
                val caravana = caravanasMap[backend.idCaravana]
                Alquiler(
                    reservaId = backend.idAlquiler.toInt(),
                    modelo = backend.modeloCaravana,
                    anio = 2024,
                    peso = "3500 kg",
                    matricula = "0000-XXX",
                    precio = 100.0,
                    precioPorDia = caravana?.precioPorDia ?: 0.0,
                    plazas = caravana?.plazas ?: 0,
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
