package com.example.rentavan.presentation.ui.viewmodel.renting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.renting.AlquilerRequest
import com.example.rentavan.data.model.renting.CaravanaDetalle
import com.example.rentavan.data.repository.renting.obtenerDetalleCaravana
import com.example.rentavan.data.repository.renting.realizarAlquiler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlquilarViewModel : ViewModel() {

    // Estado para los detalles de la caravana
    private val _detalle = MutableStateFlow<CaravanaDetalle?>(null)
    val detalle: StateFlow<CaravanaDetalle?> = _detalle

    // Estado para saber si estamos cargando algo (detalles o el alquiler)
    private val _estaCargando = MutableStateFlow(false)
    val estaCargando: StateFlow<Boolean> = _estaCargando

    // Estado para mensajes de error o éxito
    private val _mensajeFeedback = MutableStateFlow<String?>(null)
    val mensajeFeedback: StateFlow<String?> = _mensajeFeedback

    // Cargar datos al iniciar
    fun cargarDetalle(caravanaId: String) {
        viewModelScope.launch {
            _estaCargando.value = true
            val resultado = obtenerDetalleCaravana(caravanaId)
            resultado.onSuccess {
                _detalle.value = it
            }.onFailure {
                _mensajeFeedback.value = "No se pudo cargar la información"
            }
            _estaCargando.value = false
        }
    }

    // Ejecutar la acción de alquilar
    fun realizarAlquilerAction(
        caravanaId: String,
        dni: String,
        nTarjeta: String,
        nViajeros: String,
        onSuccess: () -> Unit // Callback para navegar si sale bien
    ) {
        if (dni.isEmpty() || nTarjeta.isEmpty()) {
            _mensajeFeedback.value = "Rellena los campos obligatorios"
            return
        }

        viewModelScope.launch {
            _estaCargando.value = true
            val request = AlquilerRequest(caravanaId, dni, nTarjeta, nViajeros, "2024-06-01", "2024-06-10")

            val resultado = realizarAlquiler(request)

            _estaCargando.value = false

            resultado.onSuccess { response ->
                if (response.exito) {
                    onSuccess() // Ejecutamos la navegación
                } else {
                    _mensajeFeedback.value = response.mensaje
                }
            }.onFailure {
                _mensajeFeedback.value = "Error en el servidor"
            }
        }
    }
}