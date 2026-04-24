package com.example.rentavan.data.repository.home

import com.example.rentavan.data.model.home.ResumenHome
import kotlinx.coroutines.delay

class HomeRepository {
    // Función que simula pedir los datos del usuario al servidor
    suspend fun obtenerResumenHome(): Result<ResumenHome> {
        delay(1000) // Simulamos carga de red

        // Simulamos que el servidor nos responde con estos datos:
        val resumenSimulado = ResumenHome(
            nombreUsuario = "Aitor",
            reservasActivas = 2,
            tieneNotificaciones = true
        )

        return Result.success(resumenSimulado)
    }
}