package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.profile.Caravana
import kotlinx.coroutines.delay

class MisCaravanasRepository {
    // Simulamos la petición a Spring Boot para traer los vehículos de este usuario
    suspend fun obtenerMisCaravanas(): Result<List<Caravana>> {
        delay(1500) // Simulamos internet

        // Creamos una lista falsa de vehículos
        val miLista = listOf(
            Caravana(id = "1", marca = "Volkswagen", modelo = "California T6", precioPorDia = 120.0, plazas = 4),
            Caravana(id = "2", marca = "Fiat", modelo = "Ducato Camper", precioPorDia = 95.0, plazas = 3)
        )

        return Result.success(miLista)
    }
}