package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.profile.AnadirResponse
import com.example.rentavan.data.model.profile.NuevaCaravana
import kotlinx.coroutines.delay

class AnadirAlquilerRepository {
    // Simulamos el envío de los datos de la nueva caravana
    suspend fun publicarCaravana(caravana: NuevaCaravana): Result<AnadirResponse> {
        delay(2000) // Simulamos que el servidor está procesando la subida

        // Simulamos éxito
        return Result.success(AnadirResponse(true, "¡Caravana publicada correctamente!"))
    }
}