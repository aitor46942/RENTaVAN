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

    // Estados de los campos del formulario
    var marca by mutableStateOf("")
        private set
    var modelo by mutableStateOf("")
        private set
    var precio by mutableStateOf("")
        private set
    var plazas by mutableStateOf("")
        private set
    var descripcion by mutableStateOf("")
        private set

    // Estados de la interfaz
    var isLoading by mutableStateOf(false)
        private set
    var mensajeError by mutableStateOf("")
        private set
    var subidaExitosa by mutableStateOf(false)
        private set

    // Funciones para actualizar los campos
    fun onMarcaChange(it: String) { marca = it }
    fun onModeloChange(it: String) { modelo = it }
    fun onPrecioChange(it: String) { precio = it }
    fun onPlazasChange(it: String) { plazas = it }
    fun onDescripcionChange(it: String) { descripcion = it }

    fun publicar() {
        if (marca.isBlank() || modelo.isBlank() || precio.isBlank() || plazas.isBlank()) {
            mensajeError = "Por favor, rellena los campos obligatorios"
            return
        }

        mensajeError = ""
        isLoading = true

        viewModelScope.launch {
            val nueva = NuevaCaravana(marca, modelo, precio, plazas, descripcion)
            val result = repository.publicarCaravana(nueva)

            result.onSuccess {
                isLoading = false
                subidaExitosa = true
            }.onFailure {
                isLoading = false
                mensajeError = "Error al conectar con el servidor"
            }
        }
    }
}