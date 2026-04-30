package com.example.rentavan.presentation.ui.viewmodel.renting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.renting.DisponibilidadRequest
import com.example.rentavan.data.model.renting.DisponibilidadResponse
import com.example.rentavan.data.repository.renting.consultarDisponibilidad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DisponibilidadViewModel : ViewModel() {

    private val _fechaInicio = MutableStateFlow("")
    val fechaInicio: StateFlow<String> = _fechaInicio.asStateFlow()

    private val _fechaFin = MutableStateFlow("")
    val fechaFin: StateFlow<String> = _fechaFin.asStateFlow()

    private val _disponibilidad = MutableStateFlow<DisponibilidadResponse?>(null)
    val disponibilidad: StateFlow<DisponibilidadResponse?> = _disponibilidad.asStateFlow()

    fun onFechaInicioChange(nuevaFecha: String) { _fechaInicio.value = nuevaFecha }
    fun onFechaFinChange(nuevaFecha: String) { _fechaFin.value = nuevaFecha }

    fun comprobarDisponibilidad(caravanaId: String) {
        viewModelScope.launch {
            val request = DisponibilidadRequest(caravanaId, _fechaInicio.value, _fechaFin.value)
            val resultado = consultarDisponibilidad(request) // Llamada al repositorio

            resultado.onSuccess { response ->
                _disponibilidad.value = response
            }.onFailure {
                // Manejo de error de conexión
            }
        }
    }
}