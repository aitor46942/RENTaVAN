package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.profile.CaravanaResponse
import com.example.rentavan.data.network.RetrofitClient

class MisCaravanasRepository {
    private val api = RetrofitClient.apiService

    suspend fun obtenerCaravanasPorPropietario(idPropietario: Long): Result<List<CaravanaResponse>> {
        return try {
            val response = api.listarCaravanasPorPropietario(idPropietario)
            if (response.isSuccessful && response.body() != null)
                Result.success(response.body()!!)
            else
                Result.failure(Exception("Error ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun eliminarCaravana(idCaravana: Long): Result<Unit> {
        return try {
            val response = api.eliminarCaravana(idCaravana)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error ${response.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }
}