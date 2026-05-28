package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.network.CaravanaBackendDTO
import com.example.rentavan.data.model.network.PeriodoDisponibilidadResponse
import com.example.rentavan.data.model.profile.AnadirResponse
import com.example.rentavan.data.model.profile.NuevaCaravana
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.sessions.UserSession

class AnadirAlquilerRepository {
    private val api = RetrofitClient.apiService

    suspend fun publicarCaravana(nueva: NuevaCaravana): Result<AnadirResponse> {
        val idPropietario = UserSession.idUsuario
            ?: return Result.failure(Exception("Sesión no iniciada"))

        return try {
            val dto = CaravanaBackendDTO(
                modelo = nueva.modelo,
                descripcion = nueva.descripcion,
                idPropietario = idPropietario
            )
            val caravanaResponse = api.crearCaravana(dto)
            if (!caravanaResponse.isSuccessful || caravanaResponse.body() == null) {
                return Result.failure(Exception("Error al crear la caravana: ${caravanaResponse.code()}"))
            }

            val idCaravana = caravanaResponse.body()!!.idCaravana
            val periodo = PeriodoDisponibilidadResponse(
                idCaravana = idCaravana,
                fechaInicio = nueva.fechaInicio,
                fechaFin = nueva.fechaFin
            )
            val periodoResponse = api.crearPeriodo(periodo)
            if (!periodoResponse.isSuccessful) {
                return Result.failure(Exception("Caravana creada pero error al añadir disponibilidad: ${periodoResponse.code()}"))
            }

            Result.success(AnadirResponse(true, "Caravana publicada con éxito"))
        } catch (e: Exception) { Result.failure(e) }
    }


//    suspend fun publicarCaravana(nueva: NuevaCaravana): Result<AnadirResponse> {
//        delay(1500) // Simulación de red
//        return Result.success(AnadirResponse(true, "Caravana publicada con éxito"))
//    }
}
