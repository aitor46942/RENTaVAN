package com.example.rentavan.presentation.ui.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.profile.NuevaCaravana
import com.example.rentavan.data.repository.profile.AnadirAlquilerRepository
import kotlinx.coroutines.launch

class AnadirAlquilerViewModel : ViewModel() {
    private val repository = AnadirAlquilerRepository()

    var modelo by mutableStateOf("") ; private set
    var descripcion by mutableStateOf("") ; private set
    var fechaInicio by mutableStateOf("") ; private set
    var fechaFin by mutableStateOf("") ; private set

    var isLoading by mutableStateOf(false) ; private set
    var mensajeError by mutableStateOf("") ; private set
    var subidaExitosa by mutableStateOf(false) ; private set

    fun onModeloChange(it: String) { modelo = it }
    fun onDescripcionChange(it: String) { descripcion = it }
    fun onFechaInicioChange(it: String) { fechaInicio = it }
    fun onFechaFinChange(it: String) { fechaFin = it }

    fun publicar() {
        if (modelo.isBlank()) { mensajeError = "El modelo es obligatorio"; return }
        if (fechaInicio.isBlank() || fechaFin.isBlank()) { mensajeError = "Las fechas de disponibilidad son obligatorias"; return }
        mensajeError = ""
        isLoading = true
        viewModelScope.launch {
            val nueva = NuevaCaravana(modelo, descripcion, fechaInicio, fechaFin)
            val result = repository.publicarCaravana(nueva)
            result.onSuccess { isLoading = false; subidaExitosa = true }
            result.onFailure { e -> isLoading = false; mensajeError = e.message ?: "Error al conectar con el servidor" }
        }
    }
}