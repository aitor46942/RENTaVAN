package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.profile.AnadirResponse
import com.example.rentavan.data.model.profile.NuevaCaravana
import kotlinx.coroutines.delay

class AnadirAlquilerRepository {
    suspend fun publicarCaravana(nueva: NuevaCaravana): Result<AnadirResponse> {
        delay(1500) // Simulación de red
        return Result.success(AnadirResponse(true, "Caravana publicada con éxito"))
    }
}
