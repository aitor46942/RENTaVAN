package com.example.rentavan.data.repository.renting

import com.example.rentavan.data.model.profile.CaravanaResponse
import com.example.rentavan.data.network.RetrofitClient


suspend fun obtenerCaravanasBackend(): Result<List<CaravanaResponse>> {
    return try {
        val response = RetrofitClient.apiService.listarCaravanas()
        if (response.isSuccessful && response.body() != null)
            Result.success(response.body()!!)
        else
            Result.failure(Exception("Error ${response.code()}"))
    } catch (e: Exception) { Result.failure(e) }
}
