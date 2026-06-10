package com.example.rentavan.presentation.ui.viewmodel.reservations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.repository.reservations.cancelarAlquiler
import kotlinx.coroutines.launch

class CancelacionViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var cancelacionExitosa by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf("")
        private set

    fun confirmarCancelacion(reservaId: Int) {
        viewModelScope.launch {
            isLoading = true
            mensajeError = ""
            val resultado = cancelarAlquiler(reservaId)
            resultado
                .onSuccess { cancelacionExitosa = true }
                .onFailure { e -> mensajeError = "Error al cancelar el alquiler: ${e.message}" }
            isLoading = false
        }
    }
}