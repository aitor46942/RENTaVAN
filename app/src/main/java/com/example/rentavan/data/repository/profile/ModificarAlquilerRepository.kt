package com.example.rentavan.data.repository.profile

import com.example.rentavan.data.model.profile.ActualizarCaravanaRequest
import com.example.rentavan.data.model.profile.ActualizarResponse
import kotlinx.coroutines.delay

class ModificarAlquilerRepository {

    // Simula traer los datos de la base de datos para rellenar el formulario
    suspend fun obtenerDetallesCaravana(idCaravana: String): Result<ActualizarCaravanaRequest> {
        delay(800) // Simulamos la carga

        // Devolvemos los datos por defecto que tenías en tu código
        val datosSimulados = ActualizarCaravanaRequest(
            idCaravana = idCaravana,
            modelo = "Volkswagen Grand California",
            anio = "2022",
            peso = "3500",
            matricula = "1234-ABC",
            nombreTitular = "Juan Pérez",
            telefono = "600123456",
            email = "juan@mail.com",
            fechaInicio = "01/06/2026",
            fechaFin = "31/08/2026",
            darDeAlta = true,
            numPlazas = "4",
            precio = "63"
        )
        return Result.success(datosSimulados)
    }

    // Simula guardar los cambios en el servidor
    suspend fun guardarCambios(request: ActualizarCaravanaRequest): Result<ActualizarResponse> {
        delay(1500) // Simulamos tiempo de guardado
        return Result.success(ActualizarResponse(true, "Cambios guardados correctamente"))
    }
}