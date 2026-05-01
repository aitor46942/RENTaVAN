package com.example.rentavan.presentation.ui.viewmodel.reservations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.reservations.Alquiler
import com.example.rentavan.data.model.reservations.ModificarReservaRequest
import com.example.rentavan.data.repository.reservations.modificarReserva
import com.example.rentavan.data.repository.reservations.obtenerDetalleReserva
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ModificarReservaViewModel : ViewModel() {

    private val _reservaOriginal = MutableStateFlow<Alquiler?>(null)
    val reservaOriginal: StateFlow<Alquiler?> = _reservaOriginal.asStateFlow()

    private val _numViajeros = MutableStateFlow("")
    val numViajeros: StateFlow<String> = _numViajeros.asStateFlow()

    private val _fechaInicio = MutableStateFlow("")
    val fechaInicio: StateFlow<String> = _fechaInicio.asStateFlow()

    private val _fechaFin = MutableStateFlow("")
    val fechaFin: StateFlow<String> = _fechaFin.asStateFlow()

    fun cargarDetalleReserva(reservaId: Int) {
        viewModelScope.launch {
            val resultado = obtenerDetalleReserva(reservaId)
            resultado.onSuccess { alquiler ->
                _reservaOriginal.value = alquiler
                // Inicializamos el formulario con los datos actuales
                _fechaInicio.value = alquiler.fechaInicio
                _fechaFin.value = alquiler.fechaFin
            }
        }
    }

    fun onViajerosChange(nuevoValor: String) {
        if (nuevoValor.isEmpty() || nuevoValor.all { it.isDigit() }) {
            _numViajeros.value = nuevoValor
        }
    }

    fun onFechaInicioChange(nuevaFecha: String) { _fechaInicio.value = nuevaFecha }
    fun onFechaFinChange(nuevaFecha: String) { _fechaFin.value = nuevaFecha }

    fun guardarCambios(reservaId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val request = ModificarReservaRequest(
                reservaId = reservaId,
                nViajeros = _numViajeros.value.toIntOrNull() ?: 1,
                fechaInicio = _fechaInicio.value,
                fechaFin = _fechaFin.value
            )

            val resultado = modificarReserva(request)
            resultado.onSuccess {
                onSuccess()
            }
        }
    }
}
