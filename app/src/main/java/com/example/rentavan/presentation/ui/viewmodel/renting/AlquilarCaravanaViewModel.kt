package com.example.rentavan.presentation.ui.viewmodel.renting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.renting.AlquilerRequest
import com.example.rentavan.data.model.renting.CaravanaDetalle
import com.example.rentavan.data.repository.renting.obtenerDetalleCaravana
import com.example.rentavan.data.repository.renting.realizarAlquiler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlquilarCaravanaViewModel : ViewModel() {

    // Estado del formulario
    private val _dni = MutableStateFlow("")
    val dni: StateFlow<String> = _dni.asStateFlow()

    private val _nTarjeta = MutableStateFlow("")
    val nTarjeta: StateFlow<String> = _nTarjeta.asStateFlow()

    private val _nViajeros = MutableStateFlow("")
    val nViajeros: StateFlow<String> = _nViajeros.asStateFlow()

    // Estado de la caravana
    private val _caravanaDetalle = MutableStateFlow<CaravanaDetalle?>(null)
    val caravanaDetalle: StateFlow<CaravanaDetalle?> = _caravanaDetalle.asStateFlow()

    // Estado del proceso de alquiler
    var isLoading by mutableStateOf(false)
        private set

    var alquilerExitoso by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf("")
        private set

    fun onDniChange(nuevoDni: String) { _dni.value = nuevoDni }
    fun onTarjetaChange(nuevaTarjeta: String) { _nTarjeta.value = nuevaTarjeta }
    fun onViajerosChange(nuevosViajeros: String) { _nViajeros.value = nuevosViajeros }

    fun cargarDetalles(caravanaId: String) {
        viewModelScope.launch {
            val resultado = obtenerDetalleCaravana(caravanaId)
            resultado.onSuccess { detalle -> _caravanaDetalle.value = detalle }
        }
    }

    fun confirmarAlquiler(caravanaId: String, fechaInicio: String, fechaFin: String) {
        viewModelScope.launch {
            isLoading = true
            mensajeError = ""
            val request = AlquilerRequest(
                caravanaId = caravanaId,
                dni = _dni.value,
                nTarjeta = _nTarjeta.value,
                nViajeros = _nViajeros.value,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin
            )
            val resultado = realizarAlquiler(request)
            resultado
                .onSuccess { alquilerExitoso = true }
                .onFailure { e -> mensajeError = e.message ?: "Error al realizar el alquiler. Inténtalo de nuevo." }
            isLoading = false
        }
    }
}