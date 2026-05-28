package com.example.rentavan.data.repository.renting

import com.example.rentavan.data.model.network.AlquilerBackendRequest
import com.example.rentavan.data.model.renting.AlquilerRequest
import com.example.rentavan.data.model.renting.AlquilerResponse
import com.example.rentavan.data.model.renting.CaravanaDetalle
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.sessions.UserSession

suspend fun obtenerDetalleCaravana(caravanaId: String): Result<CaravanaDetalle> {
    return try {
        val idCaravana = caravanaId.toLongOrNull()
            ?: return Result.failure(Exception("ID de caravana inválido"))

        val response = RetrofitClient.apiService.listarCaravanas()
        if (response.isSuccessful && response.body() != null) {
            val caravana = response.body()!!.find { it.idCaravana == idCaravana }
            if (caravana != null) {
                Result.success(
                    CaravanaDetalle(
                        id = caravanaId,
                        modelo = caravana.modelo,
                        anio = "—",
                        peso = "—",
                        matricula = "—",
                        informacionAdicional = caravana.descripcion
                    )
                )
            } else {
                // Fallback con datos básicos si no se encuentra la caravana
                Result.success(
                    CaravanaDetalle(
                        id = caravanaId,
                        modelo = "Caravana #$caravanaId",
                        anio = "—",
                        peso = "—",
                        matricula = "—",
                        informacionAdicional = ""
                    )
                )
            }
        } else {
            Result.failure(Exception("Error al cargar la caravana"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun realizarAlquiler(request: AlquilerRequest): Result<AlquilerResponse> {
    return try {
        val idCaravana = request.caravanaId.toLongOrNull()
            ?: return Result.failure(Exception("ID de caravana inválido"))

        val idCliente = UserSession.idUsuario
            ?: return Result.failure(Exception("Sesión no iniciada"))

        val periodosResponse = RetrofitClient.apiService.listarPeriodos(idCaravana)
        val periodos = if (periodosResponse.isSuccessful) periodosResponse.body() else null
        val idPeriodo: Long = periodos?.firstOrNull()?.idPeriodo
            ?: return Result.failure(Exception("Esta caravana no tiene periodos de disponibilidad configurados"))

        val dto = AlquilerBackendRequest(
            idCaravana = idCaravana,
            idCliente = idCliente,
            idPeriodo = idPeriodo,
            fechaInicio = request.fechaInicio,
            fechaFin = request.fechaFin
        )

        val response = RetrofitClient.apiService.crearAlquiler(dto)
        if (response.isSuccessful && response.body() != null) {
            Result.success(AlquilerResponse(exito = true, mensaje = "Alquiler realizado correctamente"))
        } else {
            val errorBody = response.errorBody()?.string()
            val detalle = if (!errorBody.isNullOrBlank()) " - $errorBody" else ""
            Result.failure(Exception("Error del servidor: ${response.code()}$detalle"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}